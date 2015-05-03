package room;

import message.generators.Clients_Message;
import message.generators.Conversationalist_Disconnected;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.exceptions.ReactionException;
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

    public ChatRoom(int maxConversationalists){
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
                    sendMessageAs(user, Conversationalist_Disconnected.message(user, toDisJoin.getNick()));
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
                        MessageSender.sendMessage(sender, Clients_Message.message(sender, user.getNick()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (EncryptionException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
