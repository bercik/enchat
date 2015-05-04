/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import app_info.CommandContainer;
import controller.ControllerManager;
import io.IOSet;
import io.IOSetFabric;
import io.display.IDisplayManager;
import io.input.IInput;
import package_forwarder.MessageIncomeBuffer;
import package_forwarder.PackageForwarder;
import plugin.PluginManager;
import util.builder.CommandContainerBuilder;

/**
 *
 * @author robert
 */
public class AppCore
{
    public AppCore()
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
            // message income buffer
            MessageIncomeBuffer messageIncomeBuffer = new MessageIncomeBuffer();
            // package forwarder
            PackageForwarder packageForwarder = new PackageForwarder(
                    messageIncomeBuffer);
            // plugin manager
            PluginManager pluginManager = new PluginManager(
                    messageIncomeBuffer, commandContainer, packageForwarder);
            // Controller Manager
            ControllerManager controllerManager
                    = new ControllerManager(displayManager, commandContainer,
                            pluginManager);
            // set controller manager reference in plugin manager
            pluginManager.setControllerManager(controllerManager);

            while (true)
            {
                // update plugin manager so it can check if there is some
                // messages from server
                pluginManager.update();
                // check if there is some new characters from user input
                input.update();
                // if input has got character
                if (input.hasChar())
                {
                    char ch = input.getChar();

                    // if escape exit application
                    if (ch == 27)
                    {
                        break;
                    }

                    // put char to controller manager
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
                // restore console to previous state
                ioSet.getInput().close();
            }
        }
    }
}
