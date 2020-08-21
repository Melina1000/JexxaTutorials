package jexxatutorials.bookstorej.domain.valueobject;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

/**
 * IMPORTANT NOTE: This is a simplified ISBN13 number which only validates the checksum because this is sufficient for this tutorial
 */
public class ISBN13
{
    private final String value;

    public ISBN13(String value)
    {
        Validate.notNull(value, "The ISBN13 number must not be null.");
        validateChecksum(value);

        this.value = value;
    }

    public String getValue()
    {
        return value;
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
        ISBN13 isbn13 = (ISBN13) o;
        return value.equals(isbn13.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value);
    }

    private void validateChecksum(String isbn13)
    {
        var digits = isbn13
                .replace("-", "")
                .toCharArray();

        Validate.isTrue(digits.length == 13,
                "Invalid ISBN number: Expected number of digits is 13 "
                        + "Given number of digits is " + digits.length);

        int digitSum = 0;

        for (int i = 0; i < digits.length - 1; ++i) //Exclude checksum value (which is at last position in digits)
        {
            var digitAsInt = Integer.parseInt(String.valueOf(digits[i]));
            digitSum += (i % 2 == 0) ? digitAsInt : digitAsInt * 3;
        }

        var calculatedCheckDigit = (10 - (digitSum % 10)) % 10;

        var expectedDigit = Integer.parseInt(String.valueOf(digits[digits.length - 1]));

        Validate.isTrue(expectedDigit == calculatedCheckDigit,
                "Invalid ISBN number: Expected checksum value is " + calculatedCheckDigit
                        + "Given value is " + expectedDigit);
    }
}
