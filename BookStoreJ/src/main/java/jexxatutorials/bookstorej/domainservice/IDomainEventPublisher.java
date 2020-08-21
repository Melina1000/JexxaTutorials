package jexxatutorials.bookstorej.domainservice;

import io.jexxa.addend.applicationcore.InfrastructureService;
import jexxatutorials.bookstorej.domain.domainevent.BookSoldOut;

@InfrastructureService
public interface IDomainEventPublisher
{
    void publish(BookSoldOut bookSoldOutEvent);
}
