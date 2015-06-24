/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

/**
 *
 * @author robert
 */
public interface IDisplayManager
{
    public void init();
    
    public void setMsg(String msg, boolean error);
    
    public void setCommand(String newCommand);
    
    public void setDisplay(IDisplay newDisplay);
    
    public String getCommand();
}
