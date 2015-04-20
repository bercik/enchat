/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

import io.display.IDisplay;

/**
 *
 * @author robert
 */
public interface IDisplayManager
{
    public void setMsg(String msg, boolean error);
    
    public void setCommand(String newCommand);
    
    public void setDisplay(IDisplay newDisplay, boolean saveCommandLine);
    
    public String getCommand();
}
