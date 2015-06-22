package model.user;

import model.ChatRoom;
import model.Account;

import java.security.PublicKey;

/**
 * Object that holds all info of user.
 *
 * @author Created by tochur on 16.05.15.
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

    /**
     * Sets user Account.
     * @param account Account, object to bind with the user.
     */
    public void bindWithAccount(Account account){
        this.account = account;
    }

    /**
     * Returns the user Account.
     * @return Account associated with the user.
     */
    public Account getAccount(){ return account; }

    /**
     * Returns the user current state.
     * @return UserState, current userState.
     */
    public UserState getState(){
        return userState;
    }

    /**
     * Sets the user state
     * @param state UserState, new userState.
     */
    public void setState(UserState state){
        this.userState = state;
    }

    /**
     * Returns the user nick.
     * @return String, user nick.
     */
    public String getNick(){
        return account.getNick();
    }

    /**
     * Returns the ChatRoom
     * @return ChatRoom, the room that user is inside.
     */
    public ChatRoom getRoom() {
        return room;
    }

    /**
     * Sets the ChatRoom
     * @param room ChatRoom, new ChatRoom that user is inside.
     */
    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    /**
     * Compares two users.
     * @param obj Object, object to compare
     * @return true if objects are equals, false otherwise.
     */
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
