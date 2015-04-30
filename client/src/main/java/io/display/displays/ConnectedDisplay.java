/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import io.display.IFormatter;

/**
 *
 * @author robert
 */
public class ConnectedDisplay extends CommandLineDisplay
{
    @Override
    public String showBody()
    {
        String body = "Jesteś teraz połączony z serwerem!\n\n" +
                "Możesz się zarejestrować wpisując komendę " + 
                formatter.fg(IFormatter.Color.YELLOW, "/register") + "\n" +
                "lub zalogować wpisując komendę " + 
                formatter.fg(IFormatter.Color.YELLOW, "/login") + "\n\n" +
                "Wpisz " + formatter.fg(IFormatter.Color.YELLOW, "/help") +
                " aby uzyskać więcej informacji";
        
        return body;
    }
}
