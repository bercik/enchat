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
import io.display.displays.HelpDisplay;
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
            commandContainer.registerCommand(-10, "test", null,
                    new TestController(), new State[]
                    {
                        State.NOT_CONNECTED,
                        State.CONNECTED
                    });
            // Controller Manager
            ControllerManager controllerManager
                    = new ControllerManager(displayManager, commandContainer);

            while (true)
            {
                input.update();
                if (input.hasChar())
                {
                    char ch = input.getChar();

                    if (ch == 27)
                    {
                        break;
                    }

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
            {
                ioSet.getInput().close();
            }
        }
    }
}

class TestController extends CommandLineController
{

    @Override
    protected void route(String input)
    {
        switch (input)
        {
            case "/connect":
                controllerManager.setAppState(State.CONNECTED);
                break;
            case "/help":
                controllerManager.setDisplay(id, new HelpDisplay(), false);
                break;
            case "/disconnect":
                controllerManager.setAppState(State.NOT_CONNECTED);
                break;
        }

        setCommand("");
    }

    @Override
    public void start(String previousCommand, String[] parameters)
    {
        setPrefix("test:");
    }

    @Override
    public void updateError(int error)
    {
    }
}
