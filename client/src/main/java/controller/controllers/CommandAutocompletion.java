/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.Configuration;
import app_info.ICommandContainer;
import controller.ControllerManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.builder.HelpCommandsBuilder;
import util.help.HelpCommands;

/**
 *
 * @author robert
 */
public class CommandAutocompletion
{
    private final ICommandContainer commandContainer;
    private final CommandLineController controller;
    private ControllerManager controllerManager;
    
    public CommandAutocompletion(ICommandContainer ccommandContainer, 
            CommandLineController ccontroller)
    {
        commandContainer = ccommandContainer;
        controller = ccontroller;
    }
    
    public void setControllerManager(ControllerManager ccontrollerManager)
    {
        controllerManager = ccontrollerManager;
    }

    private String[] concatenateArrays(String[] first, String[] second)
    {
        List<String> both = new ArrayList<>(first.length + second.length);
        Collections.addAll(both, first);
        Collections.addAll(both, second);
        return both.toArray(new String[0]);
    }
    
    private void showToUser(String[] array)
    {
        String msg = "";
        
        for (String command : array)
        {
            msg += command + ", ";
        }
        // usuwamy ostatnią spację i przecinek
        msg = msg.substring(0, msg.length() - 2);
        // wyświetlamy użytkownikowi
        controllerManager.setMsg(msg, false);
    }
    
    private String[] match(String toMatch, String[] possibilites, 
            String possibilityPrefix)
    {
        List<String> matched = new ArrayList<>();
        
        for (String possibility : possibilites)
        {
            String possibilityWithPrefix = possibilityPrefix + possibility;
            // jeżeli wpisywana komenda zaczyna się tak jak jedna z dostępnych
            // to dodajemy do listy pasujacych komend
            if (possibilityWithPrefix.startsWith(toMatch))
            {
                matched.add(possibility);
            }
        }
        
        return matched.toArray(new String[0]);
    }
    
    private boolean tryToCompleteHelp(String writeCommand)
    {
        // pobieramy prefix komend
        Configuration conf = Configuration.getInstance();
        String commandPrefix = conf.getCommandPrefix();
        
        // wszystkie mozliwe komendy helpa
        HelpCommands helpCommands = HelpCommandsBuilder.build();
        String[] possibleCommands = concatenateArrays(
                helpCommands.getAllCommandsStrings(), 
                helpCommands.getAllInformationsStrings());
        
        if (writeCommand.startsWith(commandPrefix + "help "))
        {
            String[] split = writeCommand.split(" +");
            
            if (split.length == 1)
            {
                // show all possible help commands
                showToUser(possibleCommands);
            }
            // inaczej jeżeli mamy tylko dwa łańcuchy oddzielone spacją i nie
            // kończy się spacją próbujemy dopasować
            else if (!writeCommand.endsWith(" ") && split.length == 2)
            {
                String[] matched = match(split[1], possibleCommands, "");
                
                // jeżeli jedna pasuje to ustawiamy jako komendę na kontrolerze
                if (matched.length == 1)
                {
                    int length = writeCommand.length() - split[1].length();
                    String newCommand = writeCommand.substring(0, length);
                    newCommand += matched[0];
                    controller.setCommandPermanently(newCommand);
                    
                    return true;
                }
                // jeżeli dwie lub więcej pasują to wyświetlamy możliwości
                else if (matched.length > 1)
                {
                    showToUser(matched);
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private void tryToComplete(String writeCommand)
    {
        // wszystkie komendy dostępne w aplikacji
        String[] commands = commandContainer.getAllCommands();
        // pobieramy prefix komend
        Configuration conf = Configuration.getInstance();
        String commandPrefix = conf.getCommandPrefix();
        
        String[] matchedCommands = match(writeCommand, commands, commandPrefix);
        
        // jeżeli znaleźliśmy tylko jedną pasującą komendę to ustawiamy
        // na kontrolerze
        if (matchedCommands.length == 1)
        {
            controller.setCommandPermanently(
                    commandPrefix + matchedCommands[0]);
        }
        // jeżeli dwie lub więcej to wyświetlamy użytkownikowi możliwości
        else if (matchedCommands.length > 1)
        {
            showToUser(matchedCommands);
        }
    }
    
    public void complete()
    {
        // aktualnie wpisywana komenda
        String writeCommand = controllerManager.getCommand();
        
        if (!tryToCompleteHelp(writeCommand))
        {
            tryToComplete(writeCommand);
        }
    }
}
