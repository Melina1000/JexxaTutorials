package domainservice;

import domain.domainevent.BookSoldOut;

public interface IDomainEventPublisher
{
    void publish(BookSoldOut bookSoldOutEvent);
}
