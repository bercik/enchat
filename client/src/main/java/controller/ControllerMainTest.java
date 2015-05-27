/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app_info.CommandContainer;
import io.IOSet;
import io.IOSetFabric;
import io.display.IDisplayManager;
import io.input.IInput;
import plugin.PluginManager;
import util.builder.CommandContainerBuilder;
import util.conversation.Conversation;

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
            Conversation conv = new Conversation();
            CommandContainer commandContainer = 
                    CommandContainerBuilder.build(conv);
            // plugin manager
            PluginManager pluginManager = new PluginManager(null,
                    commandContainer, null);
            // Controller Manager
            ControllerManager controllerManager
                    = new ControllerManager(displayManager, commandContainer,
                            pluginManager, conv);
            pluginManager.setControllerManager(controllerManager);

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
