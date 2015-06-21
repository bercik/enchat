/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info.exceptions;

/**
 *
 * @author robert
 */
public class BadConfigurationFileException extends Exception
{
    public BadConfigurationFileException()
    {
    }

    public BadConfigurationFileException(String message)
    {
        super(message);
    }

    public BadConfigurationFileException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BadConfigurationFileException(Throwable cause)
    {
        super(cause);
    }
}
