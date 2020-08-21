package jexxatutorials.bookstore.applicationservice;

import static jexxatutorials.bookstore.domain.aggregate.Book.newBook;

import java.util.List;
import java.util.stream.Collectors;

import jexxatutorials.bookstore.domain.aggregate.Book;
import jexxatutorials.bookstore.domain.businessexception.BookNotInStock;
import jexxatutorials.bookstore.domain.valueobject.ISBN13;
import jexxatutorials.bookstore.domainservice.IBookRepository;
import jexxatutorials.bookstore.domainservice.IDomainEventPublisher;

@SuppressWarnings("unused") // Methods which are invoked via (jexxa) driving adapter might seem unused
public class BookStoreService
{
    private final IBookRepository bookRepository;
    private final IDomainEventPublisher domainEventPublisher;

    public BookStoreService(IBookRepository bookRepository, IDomainEventPublisher domainEventPublisher)
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
