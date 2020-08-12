package jexxatutorials.timeservice.infrastructure.drivenadapter.console;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import io.jexxa.utils.JexxaLogger;
import jexxatutorials.timeservice.domainservice.ITimePublisher;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

@SuppressWarnings("unused") // Class seems to be unused since it's instantiated by Jexxa and passed as ITimePublisher to its user.
public class ConsolePublisher implements ITimePublisher
{
    private static final Logger LOGGER = JexxaLogger.getLogger(ConsolePublisher.class);

    @Override
    public void publish(LocalTime localTime)
    {
        Validate.notNull(localTime, "The passed by parameter 'localTime' must not be null.");

        var logMessage = localTime.format(DateTimeFormatter.ISO_TIME);

        LOGGER.info(logMessage);
    }
}
