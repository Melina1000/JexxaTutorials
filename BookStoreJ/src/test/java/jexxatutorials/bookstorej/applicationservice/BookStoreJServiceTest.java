package jexxatutorials.bookstorej.applicationservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.RepositoryManager;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.imdb.IMDBRepository;
import jexxatutorials.bookstorej.domain.aggregate.Book;
import jexxatutorials.bookstorej.domain.businessexception.BookNotInStock;
import jexxatutorials.bookstorej.infrastructure.drivenadapter.stub.DomainEventStubPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookStoreJServiceTest
{
    private static final String DRIVEN_ADAPTER_PERSISTENCE = "jexxatutorials.bookstorej.infrastructure.drivenadapter.persistence";
    private static final String DRIVEN_ADAPTER_MESSAGING = "jexxatutorials.bookstorej.infrastructure.drivenadapter.stub";

    private static final String ISBN_13 = "978-3-86490-387-8";

    private JexxaMain jexxaMain;

    @BeforeEach
    void initTest()
    {
        RepositoryManager.getInstance().setDefaultStrategy(IMDBRepository.class);

        jexxaMain = new JexxaMain(this.getClass().getSimpleName());
        jexxaMain.addToInfrastructure(DRIVEN_ADAPTER_PERSISTENCE)
                .addToInfrastructure(DRIVEN_ADAPTER_MESSAGING);

        DomainEventStubPublisher.clear();

        RepositoryManager.getInstance()
                .getStrategy(Book.class, Book::getISBN13, jexxaMain.getProperties())
                .removeAll();
    }

    @Test
    void addBooksToStock()
    {
        //Arrange
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreJService.class);
        var amount = 5;

        //Act
        objectUnderTest.addToStock(ISBN_13, amount);

        //Assert
        assertEquals(amount, objectUnderTest.amountInStock(ISBN_13));
    }

    @Test
    void sellBook() throws BookNotInStock
    {
        //Arrange
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreJService.class);
        var amount = 5;
        objectUnderTest.addToStock(ISBN_13, amount);

        //Act
        objectUnderTest.sell(ISBN_13);

        //Assert
        assertEquals(amount - 1, objectUnderTest.amountInStock(ISBN_13) );
    }

    @Test
    void sellBookNotInStock()
    {
        //Arrange
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreJService.class);

        //Act/Assert
        assertThrows(BookNotInStock.class, () -> objectUnderTest.sell(ISBN_13));
    }

    @Test
    void sellLastBook() throws BookNotInStock
    {
        //Arrange
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreJService.class);
        objectUnderTest.addToStock(ISBN_13, 1);

        //Act
        objectUnderTest.sell(ISBN_13);

        //Assert
        assertEquals( 0 , objectUnderTest.amountInStock(ISBN_13) );
        assertEquals(1, DomainEventStubPublisher.eventCount() );
    }
}
