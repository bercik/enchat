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
public class NullCommandException extends RuntimeException
{
    public NullCommandException()
    {
        super("Plugin or both plugin and controller are null when register in"
                + " command container");
    }
}
