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
public class ConversationFileSaveException extends Exception
{
    public ConversationFileSaveException()
    {
    }

    public ConversationFileSaveException(String message)
    {
        super(message);
    }

    public ConversationFileSaveException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ConversationFileSaveException(Throwable cause)
    {
        super(cause);
    }
}
