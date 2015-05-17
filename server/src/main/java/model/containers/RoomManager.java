package model.containers;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.ChatRoom;

import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class RoomManager {
    private Map<Integer, ChatRoom> IDRooms;

    @Inject
    public RoomManager(@Named("IDAccounts") Map<Integer, ChatRoom> IDRooms) {
        this.IDRooms = IDRooms;
    }


    public boolean isFree(Integer otherUserID) {
        Boolean inRoom = IDRooms.containsKey(otherUserID);
        if (!inRoom)
            return true;
        return false;
    }

    public void startConversation(Integer authorID, Integer otherUserID) {
        //creating Room
        ChatRoom chatRoom = new ChatRoom(authorID, otherUserID);
        //Creating connection usersToRoom
        IDRooms.put(authorID, chatRoom);
        IDRooms.put(otherUserID, chatRoom);
    }

    /**
     * Sends the message to all users in Room, except the sender.
     * @param sender - user that sends the data.
     * @param message - message to propagate
     * @throws java.io.IOException - when failed to pass the message to other users.
     */
    /*public void sendMessageAs(User sender, EncryptedMessage message) throws IOException {
        for(User user : participants){
            if (user != sender){
                messageSender.sendMessage(user.getOutStream(), message);
            }
        }
    }*/
}
