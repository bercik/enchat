/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import io.display.IFormatter;
import java.util.Arrays;
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
                ", aby uzyskać szczegółowe informacje o podanej "
                + "komendzie/informacji\n\n";
        
        HelpCommands helpCommands = HelpCommandsBuilder.build();
        Command[] allCommands = helpCommands.getAllCommands();
        Arrays.sort(allCommands);
        Information[] allInformations = helpCommands.getAllInformations();
        Arrays.sort(allInformations);
        
        result += formatter.spec(
                IFormatter.SpecialFormat.UNDERSCORE, "Komendy:") + "\n";
        for (Command c : allCommands)
        {
            result += formatCommand(c.getName(),
                    c.getParametersName(), c.getShortDescription(), 
                    false) + "\n";
        }
        
        result += formatter.spec(
                IFormatter.SpecialFormat.UNDERSCORE, "Informacje:") + "\n";
        for (Information i : allInformations)
        {
            result += formatCommand(i.getName(), new String[0], 
                    i.getShortDescription(), false) + "\n";
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
                command.getParametersName(), command.getShortDescription()) + 
                "\n";
        
        if (command.getParameters().length > 0)
        {
            result += "\n";
            result += formatter.spec(IFormatter.SpecialFormat.UNDERSCORE, 
                    "Parametry:") + "\n";

            for (Parameter param : command.getParameters())
            {
                result += formatter.bg(PARAMETER_BG_COLOR, param.getName()) + 
                        " - " + param.getDescription() + "\n";
            }
        }
        
        // usuwamy ostatni znak końca lini
        result = result.substring(0, result.length() - 1);
        
        // jeżeli jest szczegółowy opis to go wyświetlamy
        if (!command.getDescription().equals(""))
        {
            result += "\n\n" + formatter.spec(IFormatter.SpecialFormat.UNDERSCORE, 
                    "Szczegółowy opis:") + "\n";
            result += command.getDescription();
        }
                
        return result;
    }
}
