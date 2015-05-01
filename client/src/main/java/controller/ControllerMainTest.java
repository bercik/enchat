/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app_info.CommandContainer;
import app_info.Id;
import app_info.Info;
import app_info.State;
import controller.controllers.CommandLineController;
import controller.controllers.MainController;
import io.IOSet;
import io.IOSetFabric;
import io.display.IDisplayManager;
import io.display.displays.LoggedDisplay;
import io.input.IInput;

/**
 *
 * @author robert
 */
public class ControllerMainTest
{
    public static void main(String[] args)
    {
        IOSet ioSet = null;
        try
        {
            // IO
            ioSet = IOSetFabric.getIOSet();
            IDisplayManager displayManager = ioSet.getDisplayManager();
            IInput input = ioSet.getInput();
            // Command Container
            CommandContainer commandContainer = new CommandContainer();
            commandContainer.registerController(
                    Id.MAIN_CONTROLLER.getIntRepresentation(), 
                    new MainController(commandContainer), State.ALL);
            commandContainer.registerController(-10, 
                    new TestController(), 
                    new State[] { State.CONNECTED });
            // Controller Manager
            ControllerManager controllerManager = 
                    new ControllerManager(displayManager, commandContainer);
            
            while (true)
            {
                input.update();
                if (input.hasChar())
                {
                    char ch = input.getChar();
                    
                    if (ch == 27)
                        break;
                    
                    controllerManager.putChar(ch);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (ioSet != null)
                ioSet.getInput().close();
        }
    }
}

class TestController extends CommandLineController
{

    @Override
    protected void route(String input)
    {
        if (input.equals("/exit"))
            controllerManager.setAppState(State.NOT_CONNECTED);
        else
            controllerManager.setController(
                    Id.MAIN_CONTROLLER.getIntRepresentation(), null);
    }

    @Override
    public void start(String previousCommand, String[] parameters)
    {
        switch (previousCommand)
        {
            case "/test1":
                setPrefix("test1:");
                setShowCommand(false);
                break;
            case "/test2":
                setPrefix("test2:");
                setShowCommand(true);
                break;
        }
    }

    @Override
    public void updateError(int error)
    {
    }
}