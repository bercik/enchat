package controller.responders;

import com.google.inject.Inject;
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
 * Created by tochur on 18.05.15.
 */
public class Disconnect implements IMessageResponder {
    private StateManager stateManager;
    private LoggedUtil loggedUtil;
    RoomManager roomManager;
    private MessageSender messageSender;
    private Conversationalist_Disconnected conversationalist_disconnected;
    private InputStreamsHandler inputStreamsHandler;
    private OutStreams outStreams;

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
