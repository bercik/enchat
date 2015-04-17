package user;

import java.net.Socket;
import message.Message;
import message.MessageReader;
import message.MessageSender;
import rsa.PublicKeyInfo;

/**
 * Created by tochur on 16.04.15.
 *
 * Represents user that is capable to interact with server.
 */

public class ActiveUser extends MessageReader {
    private Socket clientSocket;
    /*Public key - used to encrypt messages before sending*/
    private PublicKeyInfo keyInfo;
    private UserData userData;
    private UserState userState = UserState.DISCONNECTED;

    /*Represents the user that is active (interacting with server).*/
    public ActiveUser(Socket clientSocket, PublicKeyInfo keyInfo){
        this.clientSocket = clientSocket;
        this.keyInfo = keyInfo;
        this.userState = UserState.CONNECTED_TO_SERVER;
    }

    /*Sends message to client*/
    public void sendMessage(Message message){
        MessageSender.getInstance().sendMessage(clientSocket, message);
    }

    /**
     * This methods cheeks weather new data's from client are available.
      * @return boolean - true if new data available.
     */
     public boolean checkMessageBox(){
        return true;
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
     * @return
     */
    public String getNick(){
        return userData.getNick();
    }
}
