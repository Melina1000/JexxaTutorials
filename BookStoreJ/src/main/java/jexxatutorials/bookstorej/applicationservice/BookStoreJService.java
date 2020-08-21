package jexxatutorials.bookstorej.applicationservice;

import static jexxatutorials.bookstorej.domain.aggregate.Book.newBook;

import java.util.List;
import java.util.stream.Collectors;

import io.jexxa.addend.applicationcore.ApplicationService;
import jexxatutorials.bookstorej.domain.aggregate.Book;
import jexxatutorials.bookstorej.domain.businessexception.BookNotInStock;
import jexxatutorials.bookstorej.domain.valueobject.ISBN13;
import jexxatutorials.bookstorej.domainservice.IBookRepository;
import jexxatutorials.bookstorej.domainservice.IDomainEventPublisher;

@SuppressWarnings("unused") // Methods which are invoked via (jexxa) driving adapter might seem unused
@ApplicationService
public class BookStoreJService
{
    private final IBookRepository bookRepository;
    private final IDomainEventPublisher domainEventPublisher;

    public BookStoreJService(IBookRepository bookRepository, IDomainEventPublisher domainEventPublisher)
    {
        this.bookRepository = bookRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public void addToStock(String isbn13, int amount)
    {
        var validatedISBN = new ISBN13(isbn13);

        var result = bookRepository.search(validatedISBN);
        if(result.isEmpty())
        {
            bookRepository.add(newBook(validatedISBN));
        }

        var book = bookRepository.get(validatedISBN);

        book.addToStock(amount);

        bookRepository.update(book);
    }

    public boolean inStock(String isbn13)
    {
        return inStock(new ISBN13(isbn13));
    }

    boolean inStock(ISBN13 isbn13)
    {
        return bookRepository
                .search(isbn13)
                .map(Book::inStock)
                .orElse(false);
    }

    public int amountInStock(String isbn13)
    {
        return amountInStock(new ISBN13(isbn13));
    }

    int amountInStock(ISBN13 isbn13)
    {
        return bookRepository
                .search(isbn13)
                .map(Book::getAmountInStock)
                .orElse(0);
    }

    public void sell(String isbn13) throws BookNotInStock
    {
        sell(new ISBN13(isbn13));
    }

    void sell(ISBN13 isbn13) throws BookNotInStock
    {
        var book = bookRepository
                .search(isbn13)
                .orElseThrow(BookNotInStock::new);

        var lastBookSold = book.sell();
        lastBookSold.ifPresent(domainEventPublisher::publish);

        bookRepository.update(book);
    }

    public List<ISBN13> getBooks()
    {
        return bookRepository
                .getAll()
                .stream()
                .map(Book::getISBN13)
                .collect(Collectors.toList());
    }
}
