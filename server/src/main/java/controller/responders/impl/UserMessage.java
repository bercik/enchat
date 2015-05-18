package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.responders.exceptions.NoOneActiveInConversation;
import controller.utils.state.StateManager;
import message.generators.Clients_Message;
import message.generators.Server_Message;
import message.types.UEMessage;
import model.containers.temporary.RoomManager;
import server.sender.MessageSender;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by tochur on 18.05.15.
 */
public class UserMessage implements IMessageResponder {

    private StateManager stateManager;
    private MessageSender messageSender;
    private Server_Message serverMessage;
    private Clients_Message clientsMessage;
    private RoomManager roomManager;

    @Inject
    public UserMessage(StateManager stateManager, MessageSender messageSender, RoomManager roomManager, Server_Message serverMessage, Clients_Message clientsMessage){
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.roomManager = roomManager;
        this.serverMessage = serverMessage;
        this.clientsMessage = clientsMessage;
    }


    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            //Verify user state
            stateManager.verify(ueMessage);

            //Check weather is anyone in his room
            Collection<Integer> othersInRoom =roomManager.getConversationalists(authorID);
            if (othersInRoom.size() == 0)
                throw new NoOneActiveInConversation();

            //Message to pass to all other people in room.
            toPass = serverMessage.message(ueMessage);

            for(Integer id: othersInRoom){
                try{
                    messageSender.send(toPass);
                }catch (IOException e){
                    answer = clientsMessage.message(authorID);
                }
            }
        } catch (IncorrectUserStateException e) {
            System.out.println("Got incorrect message (WRONG USER STATE)");
        } catch (NoOneActiveInConversation noOneActiveInConversation) {
            answer = clientsMessage.message(authorID);
        }

        if (answer != null){
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
    private UEMessage answer;
    private UEMessage toPass;
}
