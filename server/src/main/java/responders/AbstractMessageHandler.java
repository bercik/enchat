package responders;

import message.types.EncryptedMessage;
import message.types.Message;
import message.utils.Encryption;
import responders.exceptions.IncorrectUserStateException;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;
import user.User;
import user.UserState;

import java.io.IOException;

/**
 * Created by tochur on 24.04.15.
 */
public abstract class AbstractMessageHandler implements IMessageHandler {
    //User - author of the message
    protected User sender;
    //Message - that was received from User
    protected EncryptedMessage encrypted;
    //Holds decrypted message when it's necessary
    protected Message message;
    //Defines userStates which let him for sending this ind of message.
    protected UserState[] permittedStates;

    /**
     * Constructor of handler
     * Initialize permitted userState by invoking getPermittedStates in AbstractMessageHandler
     * @param sender - author of the message
     * @param encrypted - received message
     */
    public AbstractMessageHandler(User sender, EncryptedMessage encrypted){
        this.sender = sender;
        this.encrypted = encrypted;
        this.permittedStates = getPermittedUserStates();
    }

    /**
     * Defines standard flow for every Message handler.
     * @throws IncorrectUserStateException - if user State do not let him for sending this kind of message
     * @throws DecryptingException - when decryption failed.
     * @throws ReactionException - when reaction failed.
     */
    public void handle() throws IncorrectUserStateException, EncryptionException, ReactionException, IOException {
        validateUserState();
        processMessage();
        createAncillaryVariables();
        reaction();
    }

    /**
     * Validates weather author of the message is permitted to send this kind of message.
     * @throws IncorrectUserStateException - if user State do not let him for sending this kind of message
     */
    protected void validateUserState() throws IncorrectUserStateException {
        UserState userState = sender.getState();
        for(UserState state: permittedStates){
            if(userState == state){
                return;
            }
        }
        throw new IncorrectUserStateException();
    }

    /**
     * Let us to define politics towards an incoming message.
     * For example weather the message should be decrypted or not.
     * @throws DecryptingException - when message decrypting failed.
     */
    protected abstract void processMessage() throws DecryptingException;

    /**
     * Let us create initialize additional variables characteristic for concrete handler.
     */
    protected abstract void createAncillaryVariables();

    /**
     * Defines server reaction - response for incoming message.
     * @throws ReactionException
     */
    protected abstract void reaction() throws ReactionException, IOException, EncryptionException;

    /**
     * Prepares list of UserStates in which user is allowed to send this type of message
     * @return Array of allowed states.
     */
    protected abstract UserState[] getPermittedUserStates();

    /**
     * Reads the data from decrypted message.
     */
    protected void getDataFromMessage(){

    }

    /**
     * Decrypts the message and ensign it to this.message;
     * @throws DecryptingException - when message decrypting failed.
     */
    protected void decryptMessage() throws DecryptingException {
        this.message = Encryption.decryptMessage( encrypted, sender);
        createAncillaryVariables();
    }
}
