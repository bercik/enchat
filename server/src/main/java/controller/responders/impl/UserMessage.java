package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.responders.exceptions.NoOneActiveInConversation;
import controller.utils.state.StateManager;
import message.generators.Clients_Message;
import message.generators.Server_Message;
import message.types.EncryptedMessage;
import message.types.UEMessage;
import model.containers.temporary.RoomManager;
import server.sender.MessageSender;

import java.io.IOException;
import java.util.Collection;

/**
 * Responder, that handles UserMessages.
 *
 * @author Created by tochur on 18.05.15.
 */
public class UserMessage implements IMessageResponder {

    private StateManager stateManager;
    private MessageSender messageSender;
    private Server_Message serverMessage;
    private Clients_Message clientsMessage;
    private RoomManager roomManager;

    /**
     * Creates Responder, that handles UserMessages.
     * @param stateManager StateManager, user used to control users UserStates.
     * @param messageSender MessageSender, util used to send prepared message (UEMessages).
     * @param roomManager RoomManager, util that enables creation connection between users.
     * @param serverMessage ServerMessage, util used to easily creation of all types of message with id Server_message.
     * @param clientsMessage ClientsMessage, util used to easily creation of all types of message with id Clients_message.
     */
    @Inject
    public UserMessage(StateManager stateManager, MessageSender messageSender, RoomManager roomManager, Server_Message serverMessage, Clients_Message clientsMessage){
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.roomManager = roomManager;
        this.clientsMessage = clientsMessage;
        this.serverMessage = serverMessage;
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
        try {
            //Verify user state
            stateManager.verify(ueMessage);

            readInfo();

            //Check weather is anyone in his room
            Collection<Integer> othersInRoom = roomManager.getConversationalists(authorID);
            if (othersInRoom.size() == 0)
                throw new NoOneActiveInConversation();

            //Message to pass to all other people in room.
            EModMessage = serverMessage.message(ueMessage);

            for(Integer id: othersInRoom){
                try{
                    messageSender.send(new UEMessage(id, EModMessage));
                }catch (IOException e){
                    //Odeśłij wiadomość donadawcy o niepowodzeniu.
                    answer = clientsMessage.failedToDeliver(authorID);
                }
            }
        } catch (IncorrectUserStateException e) {
            System.out.println("Got incorrect message (WRONG USER STATE)");
        } catch (NoOneActiveInConversation noOneActiveInConversation) {
            //answer = clientsMessage.message(authorID);
        }

        if (answer == null){
            answer = clientsMessage.ok(authorID);
            try {
                messageSender.send(answer);
            } catch (IOException e) {
                System.out.println("Failed to inform user about errors.");
            }
        }
    }

    private void readInfo(){
        authorID = ueMessage.getAuthorID();
    }
    private Integer authorID;
    private UEMessage ueMessage;
    //The message to sender if some errors happened
    private UEMessage answer;
    //Message with replaced header
    private EncryptedMessage EModMessage;
}
