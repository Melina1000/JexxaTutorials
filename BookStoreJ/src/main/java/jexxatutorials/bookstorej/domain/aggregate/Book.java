package jexxatutorials.bookstorej.domain.aggregate;

import static jexxatutorials.bookstorej.domain.domainevent.BookSoldOut.bookSoldOut;

import java.util.Optional;

import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.AggregateFactory;
import io.jexxa.addend.applicationcore.AggregateID;
import jexxatutorials.bookstorej.domain.businessexception.BookNotInStock;
import jexxatutorials.bookstorej.domain.domainevent.BookSoldOut;
import jexxatutorials.bookstorej.domain.valueobject.ISBN13;

@Aggregate
public class Book
{
    private final ISBN13 isbn13;
    private int amountInStock = 0;

    private Book(ISBN13 isbn13)
    {
        this.isbn13 = isbn13;
    }

    @AggregateID
    public ISBN13 getISBN13()
    {
        return isbn13;
    }

    public int getAmountInStock()
    {
        return amountInStock;
    }

    public boolean inStock()
    {
        return amountInStock > 0;
    }

    public void addToStock(int amount)
    {
        amountInStock += amount;
    }

    public Optional<BookSoldOut> sell() throws BookNotInStock
    {
        if (!inStock())
        {
            throw new BookNotInStock();
        }

        amountInStock -= 1;

        if (!inStock())
        {
            return Optional.of(bookSoldOut(isbn13));
        }

        return Optional.empty();
    }

    @AggregateFactory(Book.class)
    public static Book newBook(ISBN13 isbn13)
    {
        return new Book(isbn13);
    }
}
