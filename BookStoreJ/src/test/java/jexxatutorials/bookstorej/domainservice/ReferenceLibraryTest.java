package jexxatutorials.bookstorej.domainservice;

import static org.junit.jupiter.api.Assertions.assertFalse;

import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.RepositoryManager;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.imdb.IMDBRepository;
import jexxatutorials.bookstorej.applicationservice.BookStoreJService;
import jexxatutorials.bookstorej.domain.aggregate.Book;
import jexxatutorials.bookstorej.stub.DomainEventStubPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReferenceLibraryTest
{
    private static final String DRIVEN_ADAPTER_PERSISTENCE = "jexxatutorials.bookstore.infrastructure.drivenadapter.persistence";
    private static final String DRIVEN_ADAPTER_MESSAGING = "jexxatutorials.bookstore.infrastructure.drivenadapter.stub";

    private JexxaMain jexxaMain;

     @BeforeEach
     void initTest()
     {
         // Here you can define the desired DB strategy without adjusting your tests
         // Within your tests you can completely focus on the domain logic which allows
         // you to run the tests as unit tests within daily development or as integration
         // tests on a build server
         RepositoryManager.getInstance().setDefaultStrategy(IMDBRepository.class);

         jexxaMain = new JexxaMain(this.getClass().getSimpleName());
         jexxaMain.addToInfrastructure(DRIVEN_ADAPTER_MESSAGING)
                 .addToInfrastructure(DRIVEN_ADAPTER_PERSISTENCE);

         DomainEventStubPublisher.clear();

         RepositoryManager.getInstance()
                 .getStrategy(Book.class, Book::getISBN13, jexxaMain.getProperties())
                 .removeAll();
     }

     @Test
     void validateAddLatestBooks()
     {
         // Arrange:
         // get the inbound port that we would like to test
         var objectUnderTest = jexxaMain.getInstanceOfPort(ReferenceLibrary.class);
         var bookStore = jexxaMain.getInstanceOfPort(BookStoreJService.class);

         // Act:
         objectUnderTest.addLatestBooks();

         //Assert:
         // after adding books via our service, our bookstore must know these books
         assertFalse(bookStore.getBooks().isEmpty());

     }
         
}
