/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import app_info.Info;
import io.display.IFormatter;

/**
 *
 * @author robert
 */
public class LoggedDisplay extends CommandLineDisplay
{
    @Override
    public String showBody()
    {
        Info info = Info.getInstance();
        
        String body = "Jesteś teraz zalogowany jako " + info.getUserName() + 
                "!\n\n" + "Aby uzyskać więcej informacji wpisz " +
                formatCommand("help");
        
        return body;
    }
}
