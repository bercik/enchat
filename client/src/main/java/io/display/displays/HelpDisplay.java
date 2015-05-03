/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import io.display.IFormatter;
import util.builder.HelpCommandsBuilder;
import util.help.Command;
import util.help.HelpCommands;
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
        String result = "To jest pomoc programu enChat\nWpisz " + 
                formatCommand("help", new String[]{ "name" }) +
                ", aby uzyskać szczegółowe informacje o podanej komendzie\n\n";
        
        HelpCommands helpCommands = HelpCommandsBuilder.build();
        Command[] allCommands = helpCommands.getAllCommands();
        Information[] allInformations = helpCommands.getAllInformations();
        
        result += formatter.spec(
                IFormatter.SpecialFormat.UNDERSCORE, "Komendy:") + "\n";
        for (Command command : allCommands)
        {
            result += formatCommand(command.getName(),
                    command.getParametersName(), command.getDescription()) + 
                    "\n";
        }
        
        result += formatter.spec(
                IFormatter.SpecialFormat.UNDERSCORE, "Informacje:") + "\n";
        for (Information information : allInformations)
        {
            result += formatter.fg(COMMAND_FG_COLOR, information.getName()) + 
                    " - " + information.getShortDescription() + "\n";
        }
        
        // remove last new line
        result = result.substring(0, result.length() - 1);
        
        return result;
    }
    
    private String showInformation()
    {
        return formatter.fg(COMMAND_FG_COLOR, information.getName()) + " - " +
                information.getShortDescription() + "\n\n" +
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
