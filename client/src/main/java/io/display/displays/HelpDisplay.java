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
public class HelpDisplay extends CommandLineDisplay
{
    @Override
    public String showBody()
    {
        String body = "To jest pomoc programu enChat\n\n" + 
                formatter.fg(COMMAND_FG_COLOR, "/connect") + 
                " - łączy z serwerem\n" +
                formatter.fg(COMMAND_FG_COLOR, "/register") + " " +
                formatter.bg(PARAMETER_BG_COLOR, "[username]") +
                " - rejestruje się na serwerze\n" + 
                formatter.fg(COMMAND_FG_COLOR, "/login") + " " +
                formatter.bg(PARAMETER_BG_COLOR, "[username]") +
                " - loguje się na serwer";
        
        return body;
    }
}
