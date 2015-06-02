/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.Configuration;
import app_info.ICommandContainer;
import app_info.State;
import controller.ControllerManager;
import io.input.Key;

/**
 *
 * @author robert
 */
public class MainController extends CommandLineController
{
    private final CommandParser commandParser;
    private final CommandHistory commandHistory;
    private final CommandAutocompletion commandAutocompletion;

    public MainController(ICommandContainer commandContainer)
    {
        super();

        commandParser = new CommandParser(commandContainer, this);
        commandHistory = new CommandHistory(this);
        commandAutocompletion = 
                new CommandAutocompletion(commandContainer, this);
    }

    @Override
    public final void putSpecialKey(Key key)
    {
        // wywołujemy metodę klasy bazowej
        super.putSpecialKey(key);
        // inaczej jeżeli konsola nie jest zablokowana to przekazujemy do
        // command history
        if (!getBlockConsole())
        {
            commandHistory.putSpecialKey(key);
        }
    }

    @Override
    protected void tabPressed()
    {
        commandAutocompletion.complete();
    }

    @Override
    public void setControllerManager(ControllerManager ccontrollerManager)
    {
        super.setControllerManager(ccontrollerManager);
        commandParser.setControllerManager(ccontrollerManager);
        commandAutocompletion.setControllerManager(ccontrollerManager);
    }

    @Override
    protected void route(String input)
    {
        setCommand("");

        if (controllerManager.getAppState().equals(State.CONVERSATION))
        {
            // jeżeli to komenda (zaczyna się od odpowiedniego prefixu) to 
            // dodajemy do historii dzięki temu unikamy sytuacji gdzie treść 
            // rozmowy jest zapisywana w historii
            Configuration conf = Configuration.getInstance();
            if (input.startsWith(conf.getCommandPrefix()))
                commandHistory.addCommand(input);
            
            // parsujemy komendę
            commandParser.parseConversation(input);
        }
        else
        {
            // dodajemy komendę do historii
            commandHistory.addCommand(input);
            // parsujemy komendę
            commandParser.parseDefault(input);
        }
    }

    @Override
    public void start(String previousCommand, String[] parameters)
    {
        setCommand("");
    }

    @Override
    public void updateError(int error)
    {
        // do nothing
        reset();
    }
}
