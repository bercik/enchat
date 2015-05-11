package room;

import message.MessageSender;
import message.generators.Conversationalist_Disconnected;
import message.types.EncryptedMessage;
import rsa.exceptions.EncryptionException;
import user.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tochur on 30.04.15.
 */
public class ChatRoom {
    private Set<User> participants = new HashSet<>();
    private MessageSender messageSender;
    private int maxSize;

    public ChatRoom(MessageSender messageSender, int maxConversationalists){
        this.messageSender = messageSender;
        this.maxSize = maxConversationalists;
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
    public void remove(User toDisJoin) throws EncryptionException, IOException {
        for(User user : participants){
            if (user == toDisJoin){
                participants.remove(toDisJoin);
            }else {
                sendMessageAs(user, Conversationalist_Disconnected.message(user, toDisJoin.getNick()));
            }
        }
    }

    /**
     * Sends the message to all users in Room, except the sender.
     * @param sender - user that sends the data.
     * @param message - message to propagate
     * @throws IOException - when failed to pass the message to other users.
     */
    public void sendMessageAs(User sender, EncryptedMessage message) throws IOException {
        System.out.print("Sending Message as");
        for(User user : participants){
            if (user != sender){
                messageSender.sendMessage(user.getOutStream(), message);
            }
        }
    }

    /**
     * How many users take part in conversation (are in ChatRoom)
     * @return - amount of users.
     */
    public int getParticipantsAmount(){
        return participants.size();
    }
}
