package jexxatutorials.bookstorej.infrastructure.drivenadapter.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import io.jexxa.infrastructure.drivenadapterstrategy.persistence.IRepository;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.RepositoryManager;
import jexxatutorials.bookstorej.domain.aggregate.Book;
import jexxatutorials.bookstorej.domain.valueobject.ISBN13;
import jexxatutorials.bookstorej.domainservice.IBookRepository;

@SuppressWarnings("unused")
public class BookRepository implements IBookRepository
{
    private final IRepository<Book, ISBN13> repository;

    private BookRepository(IRepository<Book, ISBN13> repository)
    {
        this.repository = repository;
    }

    // Factory method that requests a repository strategy from Jexxa's RepositoryManager
    public static IBookRepository create(Properties properties)
    {
        return new BookRepository(
                RepositoryManager.getInstance().getStrategy(Book.class, Book::getISBN13, properties));
    }

    @Override
    public void add(Book book)
    {
        repository.add(book);
    }

    @Override
    public Book get(ISBN13 isbn13)
    {
        return repository
                .get(isbn13)
                .orElseThrow();
    }

    @Override
    public boolean isRegistered(ISBN13 isbn13)
    {
        return repository
                .get(isbn13)
                .isPresent();
    }

    @Override
    public Optional<Book> search(ISBN13 isbn13)
    {
        return repository.get(isbn13);
    }

    @Override
    public void update(Book book)
    {
         repository.update(book);
    }

    @Override
    public List<Book> getAll()
    {
        return repository.get();
    }
}
