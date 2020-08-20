package domain.domainevent;

import java.util.Objects;

import domain.valueobject.ISBN13;

/**
 * Occurs if the last item of a book is sold, which is identified by it's ISBN13.
 */
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

    public ISBN13 getIsbn13()
    {
        return isbn13;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        BookSoldOut that = (BookSoldOut) o;
        return Objects.equals(isbn13, that.isbn13);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(isbn13);
    }
}
