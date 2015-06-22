package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.state.StateManager;
import message.generators.Another_User_Logged;
import message.types.UEMessage;
import model.containers.temporary.LoggedUtil;
import model.containers.temporary.RoomManager;
import model.exceptions.ElementNotFoundException;
import model.user.UserState;
import server.sender.MessageSender;

/**
 * @author Created by tochur on 18.05.15.
 */
public class LogOut implements IMessageResponder {
    private StateManager stateManager;
    private MessageSender messageSender;
    private Another_User_Logged logout;
    private LoggedUtil loggedUtil;
    private RoomManager roomManager;

    @Inject
    public LogOut(StateManager stateManager, MessageSender messageSender, Another_User_Logged logout, LoggedUtil loggedUtil, RoomManager roomManager){
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
        } catch(IncorrectUserStateException e){
            e.printStackTrace();
            System.out.println("Incorrect user state");
        }
    }

    public void updateModel(){
        try{
            roomManager.leaveRoom(authorID);
        }catch (ElementNotFoundException e){
            //Do nothing, cause this operation is not permitted by client - only for safety reasons
        }

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
