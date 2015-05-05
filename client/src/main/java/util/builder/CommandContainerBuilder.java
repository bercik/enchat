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
import plugin.plugins.CalcPlugin;
import plugin.plugins.ConnectPlugin;
import plugin.plugins.HelpPlugin;

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
        // login TODO change state to connected
        commandContainer.registerCommand(
                MessageId.LOG_IN.getIntRepresentation(),
                "login", null, new LoginController(),
                new State[]
                {
                    State.NOT_CONNECTED
                }, true);
        // register TODO change state to connected
        commandContainer.registerCommand(
                MessageId.SIGN_UP.getIntRepresentation(),
                "register", null, new RegisterController(),
                new State[]
                {
                    State.NOT_CONNECTED
                }, true);
        // help
        commandContainer.registerCommand(Id.HELP_PLUGIN.getIntRepresentation(),
                "help", new HelpPlugin(), null, State.ALL, false);
        // calc
        commandContainer.registerCommand(Id.CALC_PLUGIN.getIntRepresentation(),
                "calc", new CalcPlugin(), null, State.ALL, false);
        
        // connect
        commandContainer.registerCommand(
                Id.CONNECT_PLUGIN.getIntRepresentation(), "connect", 
                new ConnectPlugin(), null, new State[]
                {
                    State.NOT_CONNECTED
                }, true);
        
        return commandContainer;
    }
}
