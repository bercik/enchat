package model.user;

import model.ChatRoom;
import model.Account;

import java.security.PublicKey;

/**
 * Created by tochur on 16.05.15.
 */
public class User {
    private final int ID;
    /* Holds characteristic and constant userData */
    private Account account;
    /* Represents user current state */
    private UserState userState;
    /* Reference to controller.room, its necessary to make a conversation. */
    private ChatRoom room;
    /* Public key - used to encrypt messages before sending */
    private PublicKey publicKey;


    /**
     * Creates the user that is able to exchange messages with server.
     * @param ID - integer - unique amongst all connected users.
     * @param publicKey - receiver from stream.
     */
    public User(Integer ID, PublicKey publicKey){
        this.ID = ID;
        this.userState = UserState.CONNECTED_TO_SERVER;
        this.publicKey = publicKey;
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


    public void bindWithAccount(Account account){
        this.account = account;
    }

    public Account getAccount(){ return account; }

    public UserState getState(){
        return userState;
    }

    public void setState(UserState state){
        this.userState = state;
    }

    public String getNick(){
        return account.getNick();
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof User))return false;
        User otherUser = (User)obj;
        if ( this.getAccount().equals(otherUser.getAccount()) ) return true;
        return false;
    }
}
