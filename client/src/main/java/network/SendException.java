/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author robert
 */
public class SendException extends Exception
{
    public SendException()
    {
    }

    public SendException(String message)
    {
        super(message);
    }

    public SendException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SendException(Throwable cause)
    {
        super(cause);
    }
}
