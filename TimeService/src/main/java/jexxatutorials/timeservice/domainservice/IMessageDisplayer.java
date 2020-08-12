package jexxatutorials.timeservice.domainservice;

public interface IMessageDisplayer
{
    /**
     * This method shows the passed messageToBeDisplayed.
     *
     * @param messageToBeDisplayed the message which is going to be displayed
     */
    void display(String messageToBeDisplayed);
}
