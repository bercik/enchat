/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

import java.io.IOException;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author robert
 */
public interface IDisplay
{
    public void setMsg(String msg);
    
    public void setMsg(String msg, boolean error);
    
    public String getMsg();
    
    public void setCommand(String newCommand);
    
    public String getCommand();
    
    public String show() throws IOException, GeneratingPublicKeyException;
    
    public void setFormatter(IFormatter newFormatter);
}
