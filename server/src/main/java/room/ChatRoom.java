package room;

import message.generarators.Clients_Message;
import message.generarators.Conversationalist_Disconnected;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import rsa.exceptions.EncryptionException;
import user.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 30.04.15.
 */
public class ChatRoom {
    private List<User> participants = new LinkedList<>();
    private int maxSize;

    ChatRoom(int maxConversationalists){
        this.maxSize = maxConversationalists;
    }

    public void add(User newParticipant) throws ToMuchUsersInThisRoom {
        if ( maxSize < participants.size())
            participants.add(newParticipant);
        else
            throw new ToMuchUsersInThisRoom();
    }

    public void remove(User toDisJoin){
        for(User user : participants){
            if (user == toDisJoin){
                try {
                    sendMessageAs(user, Conversationalist_Disconnected.message(user));
                } catch (EncryptionException e) {
                    System.out.print("Informing about escaping conversation failed.");
                    e.printStackTrace();
                }
                participants.remove(toDisJoin);
            }
        }
    }

    public void sendMessageAs(User sender, EncryptedMessage message){
        for(User user : participants){
            if (user != sender){
                try {
                    MessageSender.sendMessage(user, message);
                } catch (IOException e) {
                    try {
                        MessageSender.sendMessage(sender, Clients_Message.deliveryFailed(user));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
