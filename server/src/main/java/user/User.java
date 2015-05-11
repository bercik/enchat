package user;

import message.MessageSender;
import room.ChatRoom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;

/**
 * Created by tochur on 16.04.15.
 *
 * Represents the user is system.
 * Server can interact with User - by sending encryptedMessages
 *
 * Two users are equal if UserData are the same
 *      user1.equals(user2) <=> user1.getData().equals(user2.getData());
 */

public class User {
    /* Public key - used to encrypt messages before sending */
    private PublicKey publicKey;
    /* Holds characteristic and constant userData */
    private UserData userData;
    /* Represents user current state */
    private UserState userState;
    /* Stream used for sending messages to user */
    private final DataInputStream in;
    /* Stream used for reading messages that user send to server. */
    private final DataOutputStream out;

    /* Socket let us exchange messages - after constructing it's not used, but probably reference is necessary (garbage collector) */
    private final Socket socket;
    /* Reference to room, its necessary to make a conversation. */
    private ChatRoom room;


    /**
     * Creates the user that is able to exchange messages with server.
     * @param socket - interface to user.
     * @throws IOException - Some IOException can always happen during stream creation.
     */
    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream( socket.getInputStream());
        this.out = new DataOutputStream( socket.getOutputStream());
        this.userState = UserState.CONNECTED_TO_SERVER;
    }

    /**
     * Setting publicKey, which is used to encrypt messages
     * @param publicKey - public key received from user.
     */
    public void setPublicKey(PublicKey publicKey){
        this.publicKey = publicKey;
    }

    /**
     * Returns the user public key, used to encrypt messages.
     * @return User PublicKey
     */
    public PublicKey getPublicKey(){
        return this.publicKey;
    }


    public void setData(UserData userData){
        this.userData = userData;
    }

    public UserData getData(){
        return userData;
    }

    public UserState getState(){
        return userState;
    }

    public void setState(UserState state){
        this.userState = state;
    }

    public String getNick(){
        return userData.getNick();
    }

    public String getPassword(){
        return userData.getPassword();
    }

    public DataInputStream getInputStream() throws IOException {
        return in;
    }

    public DataOutputStream getOutStream() throws IOException {
        return out;
    }

    /**
     * If no room is assigned creates new Room
     * @return ChatRoom, associated with user
     */
    public ChatRoom getRoom() {
        if(room == null){
            room = new ChatRoom(new MessageSender(), 2);
        }
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        else if ( this.getClass().equals(obj.getClass())){
            User user = (User) obj;
            if (this.getData().equals(user.getData()))
                return true;
        }
        return false;
    }
}

