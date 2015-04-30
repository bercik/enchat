package room;

import message.types.EncryptedMessage;
import message.types.Message;
import message.utils.Encryption;
import message.utils.MessageCreator;
import message.utils.MessageSender;
import user.ActiveUser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 30.04.15.
 */
public class ChatRoom {
    List<ActiveUser> participants = new LinkedList<>();

    public void add(ActiveUser newParticipant){
        participants.add(newParticipant);
    }

    public void remove(ActiveUser toDisJoin){
        for(ActiveUser activeUser: participants){
            if (activeUser == toDisJoin){
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
                    Message answer = MessageCreator.unableToDeliver(activeUser.getNick());
                    try {
                        MessageSender.sendMessage(sender, message);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
