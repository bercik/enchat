package room;

import message.generarators.Clients_Message;
import message.generarators.Conversationalist_Disconnected;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import rsa.exceptions.EncryptionException;
import user.ActiveUser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 30.04.15.
 */
public class ChatRoom {
    private List<ActiveUser> participants = new LinkedList<>();
    private int maxSize;

    ChatRoom(int maxConversationalists){
        this.maxSize = maxConversationalists;
    }

    public void add(ActiveUser newParticipant) throws ToMuchUsersInThisRoom {
        if ( maxSize < participants.size())
            participants.add(newParticipant);
        else
            throw new ToMuchUsersInThisRoom();
    }

    public void remove(ActiveUser toDisJoin){
        for(ActiveUser activeUser: participants){
            if (activeUser == toDisJoin){
                try {
                    sendMessageAs(activeUser, Conversationalist_Disconnected.message(activeUser));
                } catch (EncryptionException e) {
                    System.out.print("Informing about escaping conversation failed.");
                    e.printStackTrace();
                }
                participants.remove(toDisJoin);
            }
        }
    }

    public void sendMessageAs(ActiveUser sender, EncryptedMessage message){
        for(ActiveUser activeUser: participants){
            if (activeUser != sender){
                try {
                    MessageSender.sendMessage(activeUser, message);
                } catch (IOException e) {
                    try {
                        MessageSender.sendMessage(sender, Clients_Message.deliveryFailed(activeUser));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
