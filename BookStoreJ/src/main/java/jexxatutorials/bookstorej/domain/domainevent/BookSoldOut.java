package jexxatutorials.bookstorej.domain.domainevent;

import io.jexxa.addend.applicationcore.DomainEvent;
import jexxatutorials.bookstorej.domain.valueobject.ISBN13;

/**
 * Occurs if the last item of a book is sold, which is identified by it's ISBN13.
 */
@DomainEvent
public class BookSoldOut
{
    private final ISBN13 isbn13;

    private BookSoldOut(ISBN13 isbn13)
    {
        this.isbn13 = isbn13;
    }

    public static BookSoldOut bookSoldOut(ISBN13 isbn13)
    {
        return new BookSoldOut(isbn13);
    }

    public ISBN13 getISBN13()
    {
        return isbn13;
    }
}
