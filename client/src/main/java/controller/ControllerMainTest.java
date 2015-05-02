/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app_info.CommandContainer;
import app_info.Id;
import app_info.State;
import controller.controllers.LoginController;
import controller.controllers.MainController;
import io.IOSet;
import io.IOSetFabric;
import io.display.IDisplayManager;
import io.input.IInput;
import messages.MessageId;

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
            commandContainer.registerCommand(
                    MessageId.LOG_IN.getIntRepresentation(),
                    "login", null, new LoginController(), 
                    new State[] 
                    {
                        State.NOT_CONNECTED
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
