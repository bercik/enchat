/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import io.display.IFormatter;
import util.help.Command;
import util.help.Information;
import util.help.Parameter;

/**
 *
 * @author robert
 */
public class HelpDisplay extends CommandLineDisplay
{
    private final Command command;
    private final Information information;
    
    public HelpDisplay()
    {
        command = null;
        information = null;
    }
    
    public HelpDisplay(Command ccommand)
    {
        command = ccommand;
        information = null;
    }
    
    public HelpDisplay(Information iinformation)
    {
        information = iinformation;
        command = null;
    }
    
    @Override
    public String showBody()
    {
        if (command != null)
            return showCommand();
        if (information != null)
            return showInformation();
        
        return showDefault();
    }
    
    private String showDefault()
    {
        return "To jest pomoc programu enChat\n\n" + 
                formatCommand("connect", new String[0], "łączy z serwerem") + 
                "\n" +
                formatCommand("register", new String[] { "username" }, 
                        "rejestruje się na serwerze") + "\n" + 
                formatCommand("login", new String[] { "username" }, 
                        "loguje się na serwerze");
    }
    
    private String showInformation()
    {
        return formatter.fg(COMMAND_FG_COLOR, information.getName()) + "\n\n" +
                information.getDescription();
    }
    
    private String showCommand()
    {
        String result = formatCommand(command.getName(), 
                command.getParametersName(), command.getDescription()) + "\n";
        
        for (Parameter param : command.getParameters())
        {
            result += formatter.bg(PARAMETER_BG_COLOR, param.getName()) + 
                    " - " + param.getDescription() + "\n";
        }
        
        // remove last new line
        result = result.substring(0, result.length() - 1);
                
        return result;
    }
}
