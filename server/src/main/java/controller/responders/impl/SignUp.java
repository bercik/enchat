package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.utils.Encryption;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;

import java.security.PublicKey;

/**
 * Created by tochur on 15.05.15.
 */
public class SignUp implements IMessageResponder {
    Encryption encryption;
    StateVarifier stateVarifier;
    Accounts accounts;
    MessageSender messageSender;
    EncryptedMessage encrypted;

    @Inject
    public SignUp(Encryption encryption, StateVarifier stateVarifier, Accounts accounts, MessageSender messageSender){
        this.encryption = encryption;
        this.stateVarifier = stateVarifier;
        this.accounts = accounts;
        this.messageSender = messageSender;
    }

    @Override
    public void run() {
        try{
            Message message = encryption.encrypt(encrypted, PublicKey senderKey);
            stateVarifier.isStateOk(UserState userState, MessageID messageID);
            Account account = accounts.createNew(String nick, String password);
            EncryptedMessage answer = encryption.encrypt(Message message, PublicKey senderKey);
            messageSender.send(answer,Integer ID);
        }catch (EncryptionException e){

        }catch (IncorrectUserStateException e){

        }catch (AlreadyInCollection e){

        }catch (DecryptingException e){

        }catch (UnableToSendMessage e){

        }

    }

    @Override
    public void serveEvent(EncryptedMessage encrypted) {
        this.encrypted = encrypted;
        new Thread(this).start();
    }
}
