/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import app_info.CommandContainer;
import app_info.Configuration;
import app_info.Id;
import app_info.exceptions.BadConfigurationFileException;
import controller.ControllerManager;
import io.IOSet;
import io.IOSetFabric;
import io.display.IDisplayManager;
import io.input.IInput;
import io.input.Key;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import package_forwarder.MessageIncomeBuffer;
import package_forwarder.PackageForwarder;
import plugin.PluginManager;
import util.ExceptionUtils;
import util.Log;
import util.builder.CommandContainerBuilder;
import util.conversation.Conversation;
        
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
            // init log system
            Log.init();
            // IO
            ioSet = IOSetFabric.getIOSet();
            IDisplayManager displayManager = ioSet.getDisplayManager();
            IInput input = ioSet.getInput();
            // display manager init
            displayManager.init();
            // init input
            input.init();
            // conversation object which holds conversation messages
            Conversation conversation = new Conversation();
            // Command Container
            CommandContainer commandContainer = 
                    CommandContainerBuilder.build(conversation);
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
                            pluginManager, conversation);
            // set controller manager reference in plugin manager
            pluginManager.setControllerManager(controllerManager);
            // init configuration
            boolean successfulConfigInit = true;
            try
            {
                Configuration.init();
            }
            catch (BadConfigurationFileException | IOException ex)
            {
                successfulConfigInit = false;
                String msg = "Plik konfiguracyjny jest w złym formacie, "
                        + "używam domyślnych wartości. Aby je zmienić użyj "
                        + "komendy " + Configuration.getCommandPrefix()
                        + "config";
                pluginManager.setMsg(msg, true);
            }
            // try to connect to server at start if config file was ok
            if (successfulConfigInit)
            {
                pluginManager.updatePlugin(
                        Id.CONNECT_PLUGIN.getIntRepresentation(), new String[0]);
            }

            // działamy dopóki controller manager nie zarządzi, że koniec
            while (!controllerManager.isAppEnd())
            {
                // update plugin manager so it can check if there is some
                // messages from server
                pluginManager.update();
                // check if there is some new characters from user input
                input.update();
                // if input has got character
                if (input.hasChar())
                {
                    // get char
                    char ch = input.getChar();
                    // put char to controller manager
                    controllerManager.putChar(ch);
                }
                else if (input.hasSpecialKey())
                {
                    // get special key
                    Key key = input.getSpecialKey();
                    // put special key to controller manager
                    controllerManager.putSpecialKey(key);
                }
                
                // sleep for 1 ms to prevent all CPU usage
                Thread.sleep(0, 100);
            }
        }
        catch (Exception ex)
        {
            Logger logger = Logger.getLogger("MainLogger");
            logger.severe(ExceptionUtils.getStackTraceString(ex));
        }
        finally
        {
            // zapisujemy informację o zakończeniu logowania
            Logger logger = Logger.getLogger("MainLogger");
            logger.info("End logging");
            // zamykamy logowanie
            Log.close();
            
            if (ioSet != null)
            {
                // restore console to previous state
                ioSet.getInput().close();
            }
        }
    }
}
