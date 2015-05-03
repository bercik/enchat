/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.builder;

import app_info.CommandContainer;
import app_info.Id;
import app_info.State;
import controller.controllers.LoginController;
import controller.controllers.MainController;
import controller.controllers.RegisterController;
import messages.MessageId;

/**
 *
 * @author robert
 */
public class CommandContainerBuilder
{
    public static CommandContainer build()
    {
        CommandContainer commandContainer = new CommandContainer();
        
        // main controller
        commandContainer.registerController(
                Id.MAIN_CONTROLLER.getIntRepresentation(),
                new MainController(commandContainer), State.ALL);
        // login
        commandContainer.registerCommand(
                MessageId.LOG_IN.getIntRepresentation(),
                "login", null, new LoginController(),
                new State[]
                {
                    State.NOT_CONNECTED
                });
        // register
        commandContainer.registerCommand(
                MessageId.SIGN_UP.getIntRepresentation(),
                "register", null, new RegisterController(),
                new State[]
                {
                    State.NOT_CONNECTED
                });
        
        return commandContainer;
    }
}
