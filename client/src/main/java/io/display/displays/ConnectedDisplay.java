/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import app_info.Configuration;
import io.display.IFormatter;

/**
 *
 * @author robert
 */
public class ConnectedDisplay extends CommandLineDisplay
{
    private static final IFormatter.Color KEY_INFO_COLOR = 
            IFormatter.Color.BLUE;
    
    @Override
    public String showBody()
    {
        Configuration conf = Configuration.getInstance();
        String body = "Jesteś teraz połączony z serwerem o adresie " +
                formatter.fg(KEY_INFO_COLOR, conf.getServerAddress()) 
                + " na porcie " + 
                formatter.fg(
                        KEY_INFO_COLOR, Integer.toString(conf.getPort())) 
                + "!\n\n" +
                "Możesz się zarejestrować wpisując komendę " + 
                formatCommand("register") + "\n" +
                "lub zalogować wpisując komendę " + 
                formatCommand("login") + "\n\n" +
                "Wpisz " + formatCommand("help") +
                " aby uzyskać więcej informacji";
        
        return body;
    }
}
