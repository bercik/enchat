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

/**
 * Created by tochur on 16.04.15.
 *
 * Represents user that is capable to interact with server.
 */

public class ActiveUser{
    /*Public key - used to encrypt messages before sending*/
    private PublicKey publicKey;
    private UserData userData;
    private UserState userState = UserState.DISCONNECTED;
    private DataInputStream in;
    private DataOutputStream out;
    private ChatRoom room;


    /*Represents the user that is active.*/
    public ActiveUser(Socket clientSocket) throws IOException {
        this.in = new DataInputStream( clientSocket.getInputStream());
        this.out = new DataOutputStream( clientSocket.getOutputStream());
        this.userState = UserState.CONNECTED_TO_SERVER;
    }

    /**
     * Setting publicKey
     * @param publicKey - public key received from user.
     */
    public void setPublicKeyInfo(PublicKey publicKey){
        this.publicKey = publicKey;
    }

    /**
     * Get Public key
     * @return User PublicKey
     */
    public PublicKey getPublicKey(){
        return this.publicKey;
    }

    /**
     *
     * @param userData
     */
    public void setData(UserData userData){
        this.userData = userData;
    }

    /**
     * Gets user current state
     * @return - UserState - actual user state
     */
    public UserState getState(){
        return userState;
    }


    /**
     * Changes Current User State
     * @param state - new user State.
     */
    public void setState(UserState state){
        this.userState = state;
    }

    /**
     * Gets nick of user.
     * @return nick of the user
     */
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

