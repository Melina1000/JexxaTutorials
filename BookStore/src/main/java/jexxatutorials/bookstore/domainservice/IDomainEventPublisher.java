package jexxatutorials.bookstore.domainservice;

import jexxatutorials.bookstore.domain.domainevent.BookSoldOut;

public interface IDomainEventPublisher
{
    void publish(BookSoldOut bookSoldOutEvent);
}
