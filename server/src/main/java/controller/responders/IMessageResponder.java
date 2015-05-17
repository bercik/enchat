package controller.responders;


import message.types.UEMessage;

/**
 * Created by tochur on 14.05.15.
 */
public interface IMessageResponder extends Runnable{
    /**
     * Initialization method - passing parameter - message, that trigger event to respond.
     * It should by passed directly after constructor call.
     * It's necessary to create handlers using injections.
     * @param ueMessage
     */
    public void serveEvent(UEMessage ueMessage);
}
