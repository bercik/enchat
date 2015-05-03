/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.builder;

import util.help.Command;
import util.help.HelpCommands;
import util.help.Information;
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
        
        // help command
        name = "help";
        description = "wyświetla szczegółową pomoc o"
                    + " podanej komendzie";
        parameters = new Parameter[]
        {
            new Parameter("name", "komenda dla której ma zostać wyświetlona"
                    + " szczegółowa pomoc")
        };
        command = new Command(name, description, parameters);
        helpCommands.addCommand(name, command);
        
        // authors information
        name = "authors";
        String shortDescription = "wyświetla informację o autorach aplikacji";
        description = "Aplikacja enChat została napisana przez zespół "
                + "io_fighters jako projekt z inżynierii oprogramowania\n\n" + 
                "Członkowie zespołu: " +
                "programista/inżynier: bercik <robert.cebula1@gmail.com>\n" +
                "programista: Mati <matello455@gmail.com>";
        Information information = new Information(name, description, 
                shortDescription);
        helpCommands.addInformation(name, information);
        
        return helpCommands;
    }
}
