/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.weather;

/**
 *
 * @author robert
 */
public class CantFindWeatherException extends Exception
{
    public CantFindWeatherException()
    {
    }

    public CantFindWeatherException(String message)
    {
        super(message);
    }

    public CantFindWeatherException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CantFindWeatherException(Throwable cause)
    {
        super(cause);
    }
}
