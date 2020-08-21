package jexxatutorials.timeservice.applicationservice;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jexxatutorials.timeservice.domainservice.IMessageDisplayer;
import jexxatutorials.timeservice.domainservice.ITimePublisher;

@SuppressWarnings("unused") // Methods which are invoked via (jexxa) driving adapter might seem unused
public class TimeService
{
    private final ITimePublisher timePublisher;
    private final IMessageDisplayer messageDisplayer;

    /**
     * Note: Jexxa supports only implicit constructor injection. Therefore, we must
     * declare all required interfaces in the constructor.
     *
     * @param timePublisher required outbound port for this application service
     */
    public TimeService(ITimePublisher timePublisher, IMessageDisplayer messageDisplayer)
    {
        this.timePublisher = timePublisher;
        this.messageDisplayer = messageDisplayer;
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
     * This method publishes the current local time.
     */
    public void publishTime()
    {
        timePublisher.publish(getTime());
    }

    /**
     * This method shows the previously published time.
     * @param localTime the previously published time
     */
    public void displayPublishedTimed(LocalTime localTime)
    {
        var messageWithPublishedTime = "New Time was published, time: " + localTime.format(DateTimeFormatter.ISO_TIME);
        messageDisplayer.display(messageWithPublishedTime);
    }
}
