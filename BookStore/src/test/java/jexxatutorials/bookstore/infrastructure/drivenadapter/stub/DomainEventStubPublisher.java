package jexxatutorials.bookstore.infrastructure.drivenadapter.stub;

import java.util.ArrayList;
import java.util.List;

import jexxatutorials.bookstore.domain.domainevent.BookSoldOut;
import jexxatutorials.bookstore.domainservice.IDomainEventPublisher;

public class DomainEventStubPublisher implements IDomainEventPublisher
{
    private static final List<Object> EVENT_LIST = new ArrayList<>();

    @Override
    public void publish(BookSoldOut bookSoldOutEvent)
    {
          EVENT_LIST.add(bookSoldOutEvent);
    }

    public static int eventCount()
    {
        return EVENT_LIST.size();
    }

    public static void clear()
    {
        EVENT_LIST.clear();
    }
}
