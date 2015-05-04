/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.ICommandContainer;
import app_info.Id;
import app_info.State;
import controller.ControllerManager;
import io.display.displays.ConnectedDisplay;
import io.display.displays.HelpDisplay;

/**
 *
 * @author robert
 */
public class MainController extends CommandLineController
{
    private final CommandParser commandParser;
    
    public MainController(ICommandContainer commandContainer)
    {
        super();
        
        commandParser = new CommandParser(commandContainer, this);
    }

    @Override
    public void setControllerManager(ControllerManager ccontrollerManager)
    {
        super.setControllerManager(ccontrollerManager);
        commandParser.setControllerManager(ccontrollerManager);
    }
    
    @Override
    protected void route(String input)
    {
        setCommand("");
        String msg = "Próbuję połączyć się z serwerem...";
        controllerManager.setMsg(msg, false);
        
        if (controllerManager.getAppState().equals(State.CONVERSATION))
            commandParser.parseConversation(input);
        else
            commandParser.parseDefault(input);
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
        // TEST
        reset();
    }
}
