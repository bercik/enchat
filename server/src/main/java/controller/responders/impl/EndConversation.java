package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.state.StateManager;
import message.generators.Conversationalist_Disconnected;
import message.generators.Log_In;
import message.generators.Server_error;
import message.types.UEMessage;
import model.containers.temporary.LoggedUtil;
import model.containers.temporary.RoomManager;
import model.exceptions.ElementNotFoundException;
import model.exceptions.IncorrectNickOrPassword;
import model.user.UserState;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;
import server.sender.MessageSender;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by tochur on 18.05.15.
 */
public class EndConversation implements IMessageResponder {
    private StateManager stateManager;
    private MessageSender messageSender;
    private Conversationalist_Disconnected conversationalist_disconnected;
    private RoomManager roomManager;
    private LoggedUtil loggedUtil;

    @Inject
    public EndConversation(StateManager stateManager, MessageSender messageSender, Conversationalist_Disconnected conversationalist_disconnected, RoomManager roomManager, LoggedUtil loggedUtil){
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.conversationalist_disconnected = conversationalist_disconnected;
        this.roomManager = roomManager;
        this.loggedUtil = loggedUtil;
    }

    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    @Override
    public void run() {
        Collection<Integer> otherUsers = null;
        try{
            //Checks user state
            stateManager.verify(ueMessage);

            readInfo();
            authorNick = loggedUtil.getUserNick(authorID);
            //May save info.
            otherUsers = roomManager.leaveRoom(authorID);


            //Updating user states
            stateManager.update(authorID, UserState.LOGGED);
            stateManager.update(otherUsers, UserState.LOGGED);
            
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch (ElementNotFoundException e) {
            System.out.println("User nick not found exception.");
            e.printStackTrace();
        }

        try{
            if(otherUsers != null){
                for(Integer id: otherUsers){
                    try {
                        messageSender.send(conversationalist_disconnected.message(id, authorNick));
                    } catch (EncryptionException e) {
                        messageSender.send(conversationalist_disconnected.messageAnonymous(id));
                    }
                }
            }


        } catch (IOException e) {
            System.out.println("Unable to send message to user - answer for Log In request.");
        }
    }

    private void readInfo(){
        authorID = ueMessage.getAuthorID();
    }

    private UEMessage ueMessage;
    private Integer authorID;
    private String authorNick;
}
