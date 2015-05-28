package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Log_In;
import message.generators.Log_Out;
import message.generators.Server_error;
import message.types.UEMessage;
import message.types.UMessage;
import model.Account;
import model.containers.permanent.Authentication;
import model.containers.temporary.LoggedUtil;
import model.containers.temporary.RoomManager;
import model.exceptions.IncorrectNickOrPassword;
import model.user.UserState;
import rsa.exceptions.DecryptingException;
import server.sender.MessageSender;

import java.io.IOException;

/**
 * Created by tochur on 18.05.15.
 */
public class LogOut implements IMessageResponder {
    private StateManager stateManager;
    private MessageSender messageSender;
    private Log_Out logout;
    private LoggedUtil loggedUtil;
    private RoomManager roomManager;

    @Inject
    public LogOut(StateManager stateManager, MessageSender messageSender, Log_Out logout, LoggedUtil loggedUtil, RoomManager roomManager){
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.logout = logout;
        this.loggedUtil = loggedUtil;
        this.roomManager = roomManager;
    }

    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try{
            //Sender state verifier
            stateManager.verify(ueMessage);

            readInfo();

            updateModel();

            stateManager.update(authorID, UserState.CONNECTED_TO_SERVER);
            answer = logout.logoutSuccessful(authorID);
        } catch(IncorrectUserStateException e){
            e.printStackTrace();
            System.out.println("Incorrect user state");
        }

        try{
            messageSender.send(answer);
        } catch (IOException e) {
            System.out.println("Unable to send message to user - answer for Log out request.");
        }

    }

    public void updateModel(){
        roomManager.leaveRoom(authorID);
        //removing from logged
        loggedUtil.remove(authorID);
    }

    private void readInfo(){
        authorID = ueMessage.getAuthorID();
    }

    private UEMessage ueMessage;
    private Integer authorID;
    private UEMessage answer;
}
