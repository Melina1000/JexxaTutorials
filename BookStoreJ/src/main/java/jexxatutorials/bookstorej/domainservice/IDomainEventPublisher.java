package jexxatutorials.bookstorej.domainservice;

import jexxatutorials.bookstorej.domain.domainevent.BookSoldOut;

public interface IDomainEventPublisher
{
    void publish(BookSoldOut bookSoldOutEvent);
}
