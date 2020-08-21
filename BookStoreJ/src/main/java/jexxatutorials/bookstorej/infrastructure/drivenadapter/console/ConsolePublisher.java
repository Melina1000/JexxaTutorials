package jexxatutorials.bookstorej.infrastructure.drivenadapter.console;

import io.jexxa.utils.JexxaLogger;
import jexxatutorials.bookstorej.domain.domainevent.BookSoldOut;
import jexxatutorials.bookstorej.domainservice.IDomainEventPublisher;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class ConsolePublisher implements IDomainEventPublisher
{
    private static final Logger LOGGER = JexxaLogger.getLogger(ConsolePublisher.class);

    @Override
    public void publish(BookSoldOut bookSoldOutEvent)
    {
        Validate.notNull(bookSoldOutEvent, "BookSoldOutEvent must not be null.");

        var logMessage = bookSoldOutEvent.getClass().getSimpleName() + ": " + bookSoldOutEvent.getISBN13().getValue();

        LOGGER.info(logMessage);
    }
}
