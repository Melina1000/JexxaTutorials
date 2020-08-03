package jexxatutorials.timeservice.infrastructure.drivenadapter.console;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import io.jexxa.utils.JexxaLogger;
import jexxatutorials.timeservice.domainservice.ITimePublisher;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class ConsolePublisher implements ITimePublisher
{
    private static final Logger LOGGER = JexxaLogger.getLogger(ConsolePublisher.class);

    @Override
    public void publish(LocalTime localTime)
    {
        Validate.notNull(localTime, "Die übergebene localTime darf nicht NULL sein.");

        var logMessage = localTime.format(DateTimeFormatter.ISO_TIME);

        LOGGER.info(logMessage);
    }
}
