package jexxatutorials.timeservice.applicationservice;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import io.jexxa.utils.JexxaLogger;
import jexxatutorials.timeservice.domainservice.ITimePublisher;

public class TimeService
{
    private final ITimePublisher timePublisher;

    /**
     * Note: Jexxa supports only implicit constructor injection. Therefore, we must
     * declare all required interfaces in the constructor.
     *
     * @param timePublisher required outbound port for this application service
     */
    public TimeService(ITimePublisher timePublisher)
    {
        this.timePublisher = timePublisher;
    }

    /**
     * This method returns the current local time.
     * @return the current local time
     */
    public LocalTime getTime()
    {
        return LocalTime.now();
    }

    /**
     * This methods publishes the current local time.
     */
    public void publishTime()
    {
        timePublisher.publish(getTime());
    }

    public void showPublishedTimed(LocalTime localTime)
    {
        var logMessage = localTime.format(DateTimeFormatter.ISO_TIME);
        JexxaLogger.getLogger(TimeService.class).info("New Time was published: {} ", logMessage);
    }
}
