package jexxatutorials.bookstorej.domainservice;

import java.util.List;
import java.util.Optional;

import io.jexxa.addend.applicationcore.Repository;
import jexxatutorials.bookstorej.domain.aggregate.Book;
import jexxatutorials.bookstorej.domain.valueobject.ISBN13;

@Repository
public interface IBookRepository
{
    void add(Book book);

    Book get(ISBN13 isbn13);

    boolean isRegistered(ISBN13 isbn13);

    Optional<Book> search(ISBN13 isbn13);

    void update(Book book);

    List<Book> getAll();
}
