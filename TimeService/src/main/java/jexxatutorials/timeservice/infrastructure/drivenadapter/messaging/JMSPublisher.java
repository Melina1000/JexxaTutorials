package jexxatutorials.timeservice.infrastructure.drivenadapter.messaging;

import java.time.LocalTime;
import java.util.Properties;

import io.jexxa.infrastructure.drivenadapterstrategy.messaging.MessageSender;
import io.jexxa.infrastructure.drivenadapterstrategy.messaging.MessageSenderManager;
import jexxatutorials.timeservice.domainservice.ITimePublisher;

@SuppressWarnings("unused") //Only one implementation of interface ITimePublisher is used
public class JMSPublisher implements ITimePublisher
{
    private static final String TIME_TOPIC = "TimeService";

    private final MessageSender messageSender;

    //For all driven adapter we have to provide either a static factory or public constructor to enable implicit constructor injection
    public JMSPublisher(Properties properties)
    {
        //Request a default message Sender from corresponding strategy manager
        this.messageSender = MessageSenderManager.getInstance().getStrategy(properties);
    }

    @Override
    public void publish(LocalTime localTime)
    {
        //Send the message to the topic
        messageSender.send(localTime)
                .toTopic(TIME_TOPIC)
                .asJson();
    }
}
