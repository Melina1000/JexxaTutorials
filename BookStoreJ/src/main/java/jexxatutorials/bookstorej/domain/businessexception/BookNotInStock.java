package jexxatutorials.bookstorej.domain.businessexception;

import io.jexxa.addend.applicationcore.BusinessException;

/**
 * Is thrown when trying to sell a book that is currently not in stock
 */
@BusinessException
public class BookNotInStock extends Exception
{
}
