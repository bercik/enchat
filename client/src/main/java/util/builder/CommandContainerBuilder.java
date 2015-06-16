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
import plugin.plugins.BlockPlugin;
import plugin.plugins.CalcPlugin;
import plugin.plugins.ConnectPlugin;
import plugin.plugins.ConversationIncomePlugin;
import plugin.plugins.ConversationalistDisconnectedPlugin;
import plugin.plugins.DisconnectPlugin;
import plugin.plugins.EndTalkPlugin;
import plugin.plugins.ExitPlugin;
import plugin.plugins.HelpPlugin;
import plugin.plugins.JunkPlugin;
import plugin.plugins.LoginPlugin;
import plugin.plugins.LogoutPlugin;
import plugin.plugins.MScreenPlugin;
import plugin.plugins.MessageIncomePlugin;
import plugin.plugins.MessageOutcomePlugin;
import plugin.plugins.RegisterPlugin;
import plugin.plugins.StatePlugin;
import plugin.plugins.TalkPlugin;
import plugin.plugins.UnblockPlugin;
import plugin.plugins.UsersListPlugin;
import plugin.plugins.WeatherPlugin;
import util.conversation.Conversation;


// każda komenda, która może zmienić stan aplikacji (np. connect, login, talk)
// powinna blokować konsolę do czasu wykonania swojej czynności

/**
 *
 * @author robert
 */
public class CommandContainerBuilder
{
    public static CommandContainer build(Conversation conversation)
    {
        CommandContainer commandContainer = new CommandContainer();
        
        // main controller
        commandContainer.registerController(
                Id.MAIN_CONTROLLER.getIntRepresentation(),
                new MainController(commandContainer), State.ALL);
        // login
        commandContainer.registerCommand(
                MessageId.LOG_IN.getIntRepresentation(),
                "login", new LoginPlugin(), new LoginController(),
                new State[]
                {
                    State.CONNECTED
                }, true);
        // logout
        commandContainer.registerCommand(
                MessageId.LOGOUT.getIntRepresentation(), "logout",
                new LogoutPlugin(), null, new State[]
                {
                    State.LOGGED
                }, true);
        // register
        commandContainer.registerCommand(
                MessageId.SIGN_UP.getIntRepresentation(),
                "register", new RegisterPlugin(), new RegisterController(),
                new State[]
                {
                    State.CONNECTED
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
        
        // disconnect
        commandContainer.registerCommand(
                Id.DISCONNECT_PLUGIN.getIntRepresentation(), "disconnect", 
                new DisconnectPlugin(), null, new State[]
                {
                    State.CONNECTED
                }, true);
        
        // state
        commandContainer.registerCommand(Id.STATE_PLUGIN.getIntRepresentation(),
                "state", new StatePlugin(), null, State.ALL, false);
        
        // lists
        String header;
        String message;
        // logged users list
        header = "Zalogowani użytkownicy";
        message = "Pobieram listę zalogowanych użytkowników";
        commandContainer.registerCommand(
                MessageId.CLIENTS_LIST.getIntRepresentation(), 
                "users", new UsersListPlugin(header, message), 
                null, new State[]
                {
                    State.LOGGED,
                    State.CONVERSATION
                }, true);
        
        // blacklist
        header = "Czarna lista";
        message = "Pobieram czarną listę użytkowników";
        commandContainer.registerCommand(
                MessageId.BLACK_LIST.getIntRepresentation(), 
                "blacklist", new UsersListPlugin(header, message), 
                null, new State[]
                {
                    State.LOGGED,
                    State.CONVERSATION
                }, true);
        
        // block
        commandContainer.registerCommand(
                MessageId.ADD_TO_BLACK_LIST.getIntRepresentation(), 
                "block", new BlockPlugin(), null, new State[]
                {
                    State.LOGGED,
                    State.CONVERSATION
                }, false);
        
        // unblock
        commandContainer.registerCommand(
                MessageId.REMOVE_FROM_BLACK_LIST.getIntRepresentation(), 
                "unblock", new UnblockPlugin(), null, new State[]
                {
                    State.LOGGED,
                    State.CONVERSATION
                }, false);
        
        // junk
        commandContainer.registerPlugin(MessageId.JUNK.getIntRepresentation(),
                new JunkPlugin(), State.ALL);
        
        // weather
        commandContainer.registerCommand(
                Id.WEATHER_PLUGIN.getIntRepresentation(), "weather", 
                new WeatherPlugin(), null, State.ALL, false);
        
        // mscreen
        commandContainer.registerCommand(
                Id.MSCREEN_PLUGIN.getIntRepresentation(), "mscreen", 
                new MScreenPlugin(), null, State.ALL, false);
        
        // exit
        commandContainer.registerCommand(Id.EXIT_PLUGIN.getIntRepresentation(), 
                "exit", new ExitPlugin(), null, State.ALL, true);
        
        // -----------Conversation commands and plugins------------------------
        
        // talk
        commandContainer.registerCommand(
                MessageId.CONVERSATION_REQUEST.getIntRepresentation(), 
                "talk", new TalkPlugin(conversation), null, new State[]
                {
                    State.LOGGED
                }, false); // TODO change to true
        
        // conversation income
        commandContainer.registerPlugin(
                MessageId.INCOMING_CONVERSATION.getIntRepresentation(), 
                new ConversationIncomePlugin(conversation), new State[]
                {
                    State.CONVERSATION
                });
        
        // client message
        commandContainer.registerPlugin(
                MessageId.CLIENT_MESSAGE.getIntRepresentation(),
                new MessageOutcomePlugin(conversation), new State[]
                {
                    State.CONVERSATION
                });
        
        // server message
        commandContainer.registerPlugin(
                MessageId.SERVER_MESSAGE.getIntRepresentation(), 
                new MessageIncomePlugin(conversation), new State[]
                {
                    State.CONVERSATION
                });
        
        // endtalk
        commandContainer.registerCommand(
                MessageId.END_TALK.getIntRepresentation(), 
                "endtalk", new EndTalkPlugin(conversation), null, new State[]
                {
                    State.CONVERSATION
                }, true);
        
        // conversationalist disconnected
        commandContainer.registerPlugin(
                MessageId.CONVERSATIONALIST_DISCONNECTED.getIntRepresentation(),
                new ConversationalistDisconnectedPlugin(conversation),
                new State[]
                {
                    State.CONVERSATION
                });
        
        // ---------end of conversation commands and plugins-------------------
        
        return commandContainer;
    }
}
