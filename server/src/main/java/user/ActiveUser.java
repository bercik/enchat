package user;

import message.types.EncryptedMessage;
import message.utils.MessageSender;
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
    private Socket clientSocket;
    /*Public key - used to encrypt messages before sending*/
    private PublicKeyInfo keyInfo;
    private PublicKey publicKey;
    private UserData userData;
    private UserState userState = UserState.DISCONNECTED;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean canCheckBuffer = true;


    /*Represents the user that is active.*/
    public ActiveUser(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.userState = UserState.CONNECTED_TO_SERVER;
        this.in = new DataInputStream( clientSocket.getInputStream());
        this.out = new DataOutputStream( clientSocket.getOutputStream());
    }

    /*Represents the user that is active (interacting with server).*/
    public ActiveUser(Socket clientSocket, PublicKeyInfo keyInfo) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.clientSocket = clientSocket;
        this.keyInfo = keyInfo;
        this.publicKey = keyInfo.getPublicKey();
        this.userState = UserState.CONNECTED_TO_SERVER;
    }

    public PublicKey getPublicKey(){
        return this.publicKey;
    }

    /*Sends message to client*/
    public void sendMessage(EncryptedMessage message) throws IOException {
        MessageSender.sendMessage(this, message);
    }


    /**
     * Handle next message from user.
     */
    public void handleMessage(){
    }

    /**
     * Sets user personal data
     * @param userData - UserData, personal user Data
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

    /***
     * Changing user state
     * @param clientState - new user sate.
     */
    public void setState(UserState clientState){
        this.userState = clientState;
    }

    /**
     * Gets nick of user.
     * @return nick of the user
     */
    public String getNick(){
        return userData.getNick();
    }

    /*public Socket getSocket() {
        return clientSocket;
    }*/

    public PublicKeyInfo getKeyInfo() {
        return keyInfo;
    }

    public void setKeyInfo(PublicKeyInfo keyInfo) {
        this.keyInfo = keyInfo;
    }

    public DataInputStream getInputStream() throws IOException {
        if(in == null)
            in = new DataInputStream( clientSocket.getInputStream() );
        return in;
    }

    public DataOutputStream getOutStream() throws IOException {
        if( out == null )
            out = new DataOutputStream(clientSocket.getOutputStream());
        return out;
    }

    public void setPublicKeyInfo(PublicKeyInfo clientPublicKeyInfo) {
        try {
            this.keyInfo = clientPublicKeyInfo;
            this.publicKey = keyInfo.getPublicKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public boolean isBufferFree() {
        return canCheckBuffer;
    }

    public void setCanCheckBuffer(boolean canCheckBuffer) {
        this.canCheckBuffer = canCheckBuffer;
    }
}

