package jexxatutorials.timeservice.infrastructure.drivenadapter.visualoutput;

import io.jexxa.utils.JexxaLogger;
import jexxatutorials.timeservice.domainservice.IMessageDisplayer;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

@SuppressWarnings("unused") // Class seems to be unused since it's instantiated by Jexxa and passed as IMessageDisplayer to its user.
public class ConsoleOutput implements IMessageDisplayer
{
    static final Logger LOGGER = JexxaLogger.getLogger(ConsoleOutput.class);


    @Override
    public void display(String messageToBeDisplayed)
    {
        Validate.notNull(messageToBeDisplayed, "The passed by parameter 'messageToBeDisplayed' must not be null.");

        LOGGER.info(messageToBeDisplayed);
    }
}
