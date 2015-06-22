package message.exceptions;

/**
 * When any exception, during reading from Socket new message happens.
 * Ex. when message has wrong format (wrong header type) or not full message was send.
 *
 * @author Created by tochur on 03.05.15.
 */
public class IncorrectBufferRead extends IncomingMessageException {
}
