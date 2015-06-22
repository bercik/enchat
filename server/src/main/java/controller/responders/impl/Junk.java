package controller.responders.impl;

import controller.responders.IMessageResponder;
import message.types.UEMessage;

/**
 * Creates Responder, that handles Junk messages.
 *
 * @author Created by tochur on 17.05.15.
 */
public class Junk implements IMessageResponder {

    /**
     * Now it is empty method.
     */
    @Override
    public void serveEvent(UEMessage ueMessage) {}

    /**
     * Now it is empty method.
     */
    @Override
    public void run() {}
}
