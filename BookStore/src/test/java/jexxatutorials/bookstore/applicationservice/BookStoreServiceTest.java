package jexxatutorials.bookstore.applicationservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.RepositoryManager;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.imdb.IMDBRepository;
import jexxatutorials.bookstore.domain.aggregate.Book;
import jexxatutorials.bookstore.domain.businessexception.BookNotInStock;
import jexxatutorials.bookstore.infrastructure.drivenadapter.stub.DomainEventStubPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookStoreServiceTest
{
    private static final String DRIVEN_ADAPTER_PERSISTENCE = "jexxatutorials.bookstore.infrastructure.drivenadapter.persistence";
    private static final String DRIVEN_ADAPTER_MESSAGING = "jexxatutorials.bookstore.infrastructure.drivenadapter.stub";

    private static final String isbn13 = "978-3-86490-387-8";

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
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreService.class);
        var amount = 5;

        //Act
        objectUnderTest.addToStock(isbn13, amount);

        //Assert
        assertEquals(amount, objectUnderTest.amountInStock(isbn13));
    }

    @Test
    void sellBook() throws BookNotInStock
    {
        //Arrange
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreService.class);
        var amount = 5;
        objectUnderTest.addToStock(isbn13, amount);

        //Act
        objectUnderTest.sell(isbn13);

        //Assert
        assertEquals( amount - 1, objectUnderTest.amountInStock(isbn13) );
    }

    @Test
    void sellBookNotInStock()
    {
        //Arrange
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreService.class);

        //Act/Assert
        assertThrows(BookNotInStock.class, () -> objectUnderTest.sell(isbn13));
    }

    @Test
    void sellLastBook() throws BookNotInStock
    {
        //Arrange
        var objectUnderTest = jexxaMain.getInstanceOfPort(BookStoreService.class);
        objectUnderTest.addToStock(isbn13, 1);

        //Act
        objectUnderTest.sell(isbn13);

        //Assert
        assertEquals( 0 , objectUnderTest.amountInStock(isbn13) );
        assertEquals(1, DomainEventStubPublisher.eventCount() );
    }
}
