package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.state.StateManager;
import message.generators.Conversationalist_Disconnected;
import message.types.UEMessage;
import model.containers.temporary.LoggedUtil;
import model.containers.temporary.RoomManager;
import model.exceptions.ElementNotFoundException;
import model.user.UserState;
import server.listeners.message.InputStreamsHandler;
import server.sender.MessageSender;
import server.sender.OutStreams;

/**
 * Responder, that handles DISCONNECT messages.
 *
 * @author Created by tochur on 18.05.15.
 */
public class Disconnect implements IMessageResponder {
    private StateManager stateManager;
    private LoggedUtil loggedUtil;
    private RoomManager roomManager;
    private MessageSender messageSender;
    private Conversationalist_Disconnected conversationalist_disconnected;
    private InputStreamsHandler inputStreamsHandler;
    private OutStreams outStreams;

    /**
     * Creates Responder, that handles LogIN messages.
     * @param stateManager StateManager, user used to control users UserStates.
     * @param messageSender MessageSender, util used to send prepared message (UEMessages).
     * @param roomManager RoomManager, util that enables creation connection between users.
     * @param conversationalist_disconnected Conversationalist_Disconnected, util used to easily creation of all types of message with id Conversationalist_Disconnected.
     * @param loggedUtil LoggedUtil, util used to log users.
     * @param inputStreamsHandler InputStreamHandler, util that handles Input buffers, scan for new messages, and reads them.
     * @param outStreams OutStreams, util used to access output streams.
     */
    @Inject
    public Disconnect(StateManager stateManager, MessageSender messageSender, RoomManager roomManager, Conversationalist_Disconnected conversationalist_disconnected, LoggedUtil loggedUtil, InputStreamsHandler inputStreamsHandler, OutStreams outStreams){
        this.stateManager = stateManager;
        this.roomManager = roomManager;
        this.messageSender = messageSender;
        this.conversationalist_disconnected = conversationalist_disconnected;
        this.loggedUtil = loggedUtil;
        this.conversationalist_disconnected = conversationalist_disconnected;
        this.inputStreamsHandler = inputStreamsHandler;
        this.outStreams = outStreams;
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

            roomManager.leaveRoomAndTryToInform(messageSender, authorID, authorNick, conversationalist_disconnected);
            inputStreamsHandler.deleteInputWithId(authorID);
            outStreams.deleteStreamWithId(authorID);
            stateManager.update(authorID, UserState.DISCONNECTED);
        } catch(IncorrectUserStateException e){
            e.printStackTrace();
            System.out.println("Incorrect user state");
        } catch (ElementNotFoundException e) {
            //Find some solution
        }
    }

    private void readInfo() throws ElementNotFoundException {
        authorID = ueMessage.getAuthorID();
        authorNick = loggedUtil.getUserNick(authorID);
    }

    private UEMessage ueMessage;
    private Integer authorID;
    private String authorNick;
}
