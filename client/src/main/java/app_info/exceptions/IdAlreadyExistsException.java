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
public class IdAlreadyExistsException extends RuntimeException
{
    public IdAlreadyExistsException()
    {
        super("Id already exists in command container");
    }
}
