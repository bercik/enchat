package controller.room;


import controller.user.User;
import message.generators.Messages;
import message.types.EncryptedMessage;
import newServer.sender.MessageSender;
import rsa.exceptions.EncryptionException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tochur on 30.04.15.
 */
public class ChatRoom {
    private Set<User> participants = new HashSet<>();
    private MessageSender messageSender;
    private Messages messages;
    private int maxSize;

    public ChatRoom(MessageSender messageSender, int maxConversationalists, Messages messages){
        this.messageSender = messageSender;
        this.maxSize = maxConversationalists;
        this.messages = messages;
    }

    /**
     * Adds new user to ChatRoom,
     * @param newParticipant - user to add
     * @throws ToMuchUsersInThisRoom - when no more participants are allowed.
     */
    public void add(User newParticipant) throws ToMuchUsersInThisRoom {
        if ( maxSize > participants.size())
            participants.add(newParticipant);
        else
            throw new ToMuchUsersInThisRoom();
    }

    /**
     * Let the user leave the ChatRoom, all conversationalists are informing.
     * @param toDisJoin - leaving user
     * @throws EncryptionException - when failed to encrypt message informing about the leaving fact.
     * @throws IOException - when error during sending message occurs
     */
   /* public void remove(User toDisJoin) throws EncryptionException, IOException {
        for(User user : participants){
            if (user == toDisJoin) {
                participants.remove(toDisJoin);
                sendMessageAs(user, messages.conversationalistDisconnected().message(user, toDisJoin.getNick()));
                break;
            }
        }
    }*/

    /**
     * Sends the message to all users in Room, except the sender.
     * @param sender - user that sends the data.
     * @param message - message to propagate
     * @throws IOException - when failed to pass the message to other users.
     */
    /*public void sendMessageAs(User sender, EncryptedMessage message) throws IOException {
        for(User user : participants){
            if (user != sender){
                messageSender.sendMessage(user.getOutStream(), message);
            }
        }
    }*/

    /**
     * How many users take part in conversation (are in ChatRoom)
     * @return - amount of users.
     */
    public int getParticipantsAmount(){
        return participants.size();
    }
}
