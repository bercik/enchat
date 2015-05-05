/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.Configuration;
import app_info.ICommandContainer;
import controller.ControllerManager;
import java.util.Arrays;

/**
 *
 * @author robert
 */
public class CommandParser
{
    private final ICommandContainer commandContainer;
    private final CommandLineController controller;
    private ControllerManager controllerManager;
    
    public CommandParser(ICommandContainer ccommandContainer, 
            CommandLineController ccontroller)
    {
        commandContainer = ccommandContainer;
        controller = ccontroller;
    }
    
    public void setControllerManager(ControllerManager ccontrollerManager)
    {
        controllerManager = ccontrollerManager;
    }
    
    public void parseDefault(String input)
    {
        // obcinamy białe znaki na początku i końcu wejścia
        input = input.trim();
        // rozdzielamy komendę od parametrów
        CommandParametersPair cpp = splitToCommandParameters(input);
        String command = cpp.command;
        String[] parameters = cpp.parameters;
        
        // sprawdzamy czy komenda jest poprawną komendą
        if (!commandContainer.isCommand(cpp.command))
        {
            Configuration conf = Configuration.getInstance();
            String msg = command + " nie jest komendą. Każda komenda musi " +
                    "zaczynać się od " + conf.getCommandPrefix();
            controllerManager.setMsg(msg, true);
            return;
        }
        
        // sprawdzamy czy komenda istnieje
        if (!commandContainer.commandExists(command))
        {
            String msg = "Komenda " + command + " nie istnieje." +
                    " Wpisz /help aby uzyskać pomoc";
            controllerManager.setMsg(msg, true);
            return;
        }
        
        // pobieramy id komendy
        int commandId = commandContainer.getIdByString(command);
        // sprawdzamy czy można wywołać daną komendę w aktualnym stanie aplikacji
        if (!commandContainer.checkCommandAvailability(
                commandId, controllerManager.getAppState()))
        {
            String msg = "Komenda " + command + " nie może zostać " +
                    "uruchomiona w stanie " + 
                    controllerManager.getAppState().toString();
            controllerManager.setMsg(msg, true);
            return;
        }
        // sprawdzamy czy istnieje kontroler dla danego id
        if (commandContainer.hasController(commandId))
        {
            // zmieniamy kontroler
            controllerManager.setController(commandId, parameters);
        }
        // sprawdzamy czy istnieje plugin dla danego id
        else if (commandContainer.hasPlugin(commandId))
        {
            // sprawdzamy i blokujemy konsolę
            if (commandContainer.checkBlockConsole(commandId))
                controller.setBlockConsole(true);
            // uruchamiamy plugin
            controllerManager.startPlugin(commandId, parameters);
        }
        else
        {
            // rzucamy wyjątek
            throw new RuntimeException("Dla komendy " + command +
                    " nie istnieje kontroler ani plugin");
        }
    }
    
    public void parseConversation(String input)
    {
        // jeżeli zaczyna się od znaku komendy to traktujemy 
        // jak zawsze
        Configuration conf = Configuration.getInstance();
        if (input.startsWith(conf.getCommandPrefix()))
        {
            parseDefault(input);
        }
        // inaczej traktujemy jako wiadomość
        else
        {
            // TODO
            // something like this
            // pluginManager.startPlugin(MessageId.Conversation, msg)
        }
    }
    
    private CommandParametersPair splitToCommandParameters(String input)
    {
        String[] split = input.split("\\s+");
        
        String command = split[0];
        String[] parameters = Arrays.copyOfRange(split, 1, split.length);
        
        return new CommandParametersPair(command, parameters);
    }
    
    private static class CommandParametersPair
    {
        public final String command;
        public final String[] parameters;

        public CommandParametersPair(String command, String[] parameters)
        {
            this.command = command;
            this.parameters = parameters;
        }
    }
}
