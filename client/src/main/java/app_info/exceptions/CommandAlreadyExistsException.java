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
public class CommandAlreadyExistsException extends Exception
{
    public CommandAlreadyExistsException()
    {
        super("Command already exists in command container");
    }
}
