/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app_info.CommandContainer;
import app_info.Id;
import app_info.State;
import controller.controllers.CommandLineController;
import controller.controllers.LoginController;
import controller.controllers.MainController;
import controller.controllers.RegisterController;
import io.IOSet;
import io.IOSetFabric;
import io.display.IDisplayManager;
import io.display.displays.HelpDisplay;
import io.input.IInput;
import messages.MessageId;
import util.builder.CommandContainerBuilder;
import util.builder.HelpCommandsBuilder;
import util.help.Command;
import util.help.HelpCommands;
import util.help.Information;
import util.help.Parameter;

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
            CommandContainer commandContainer = CommandContainerBuilder.build();
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
