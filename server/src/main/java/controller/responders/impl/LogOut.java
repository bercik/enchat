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
 * Responder, that handles LogOut messages.
 *
 * @author Created by tochur on 18.05.15.
 */
public class LogOut implements IMessageResponder {
    private StateManager stateManager;
    private LoggedUtil loggedUtil;
    private RoomManager roomManager;

    /**
     * Creates Responder, that handles LogOut messages.
     * @param stateManager StateManager, user used to control users UserStates.
     * @param loggedUtil LoggedUtil, util used to log users.
     * @param roomManager RoomManager, util that enables creation connection between users.
     */
    @Inject
    public LogOut(StateManager stateManager, LoggedUtil loggedUtil, RoomManager roomManager){
        this.stateManager = stateManager;
        this.loggedUtil = loggedUtil;
        this.roomManager = roomManager;
    }

    /**
     * Starts the responder as a Thread.
     * @param ueMessage UEMessage, message that will be handled by responder.
     */
    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    /**
     * Function that calls actions on utils passed
     */
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

    /**
     * Updates the state after action.
     */
    void updateModel(){
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
}
