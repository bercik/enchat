/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.conversation;

/**
 *
 * @author robert
 */
public class ConversationFileReadException extends Exception
{
    public ConversationFileReadException()
    {
    }

    public ConversationFileReadException(String message)
    {
        super(message);
    }

    public ConversationFileReadException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ConversationFileReadException(Throwable cause)
    {
        super(cause);
    }
}
