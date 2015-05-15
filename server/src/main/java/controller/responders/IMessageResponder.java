package controller.responders;

import message.types.EncryptedMessage;

/**
 * Created by tochur on 14.05.15.
 */
public interface IMessageResponder extends Runnable{
    public void serveEvent(EncryptedMessage encrypted);
}
