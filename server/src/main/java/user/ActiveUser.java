package user;

import message.types.EncryptedMessage;
import message.utils.MessageSender;
import room.ChatRoom;
import rsa.PublicKeyInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by tochur on 16.04.15.
 *
 * Represents user that is at least capable to interact with server.
 * It can change it's state
 */

public class ActiveUser{
    /* Public key - used to encrypt messages before sending */
    private PublicKey publicKey;
    /* Holds characteristic and constant userData */
    private UserData userData;
    /* Represents user current state */
    private UserState userState;
    /* Stream used for sending messages to user */
    private DataInputStream in;
    /* Stream used for reading messages that user send to server. */
    private DataOutputStream out;
    /* Socket let us exchange messages - after constructing it's not used, but probably reference is necessary (garbage collector) */
    private Socket clientSocket;
    /* Reference to room, its necessary to make a conversation. */
    private ChatRoom room;


    /**
     * Creates the user that is able to exchange messages with server.
     * @param clientSocket - interface to user.
     * @throws IOException - Some IOException can always happen during stream creation.
     */
    public ActiveUser(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new DataInputStream( clientSocket.getInputStream());
        this.out = new DataOutputStream( clientSocket.getOutputStream());
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

    public UserData getUserData(){
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

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }
}

