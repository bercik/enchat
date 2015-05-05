/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exceptions;

/**
 *
 * @author robert
 */
public class RunMethodNotImplementedException extends RuntimeException
{
    public RunMethodNotImplementedException()
    {
        super("Run method in class inherited from IState interface is not "
                + "implemented and shouldn't be called");
    }
}
