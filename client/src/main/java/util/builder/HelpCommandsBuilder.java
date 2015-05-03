/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.builder;

import util.help.Command;
import util.help.HelpCommands;
import util.help.Parameter;

/**
 *
 * @author robert
 */
public class HelpCommandsBuilder
{
    public static HelpCommands build()
    {
        HelpCommands helpCommands = new HelpCommands();
        
        // register command
        String name = "register";
        String description = "umożliwia rejestrację użytkownika na serwerze";
        Parameter[] parameters = new Parameter[]
        {
            new Parameter("username", "nazwa użytkownika, 4-8 liter, cyfr lub"
                    + " znaków podkreślenia"),
            new Parameter("password", "hasło, 4-12 znaków")
        };
        Command command = new Command(name, description, parameters);
        helpCommands.addCommand(name, command);
        
        return helpCommands;
    }
}
