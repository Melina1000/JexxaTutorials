package jexxatutorials.bookstore.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ISBN13Test
{
    @Test
    void testValidISBN13()
    {
        //Arrange
        var isbn13 = "978-3-86490-387-8";

        //Act and assert
        assertDoesNotThrow(() -> new ISBN13(isbn13));
    }

    @Test
    void testInvalidISBN13()
    {
        //Arrange
        var isbn13 = "978-3-86490-387-0";

        //Act and assert
        assertThrows(IllegalArgumentException.class, () -> new ISBN13(isbn13));
    }
}
