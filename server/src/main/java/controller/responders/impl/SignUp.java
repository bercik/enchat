package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.exceptions.UnableToSendMessage;
import message.generators.Server_error;
import message.generators.Sign_Up;
import message.types.EncryptedMessage;
import message.types.UEMessage;
import message.types.UMessage;
import messages.MessageId;
import model.containers.permanent.Registration;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import newServer.sender.MessageSender;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;

import java.io.IOException;

/**
 * Created by tochur on 15.05.15.
 */
public class SignUp implements IMessageResponder {
    private Decryption decryption;
    private Sign_Up sign_up;
    private StateManager stateManager;
    private Registration registration;
    private MessageSender messageSender;
    protected UEMessage ueMessage;

    private Integer authorID;
    private String nick;
    private String password;
    private UMessage message;
    private UEMessage answer;

    @Inject
    public SignUp(Decryption decryption, StateManager stateManager, MessageSender messageSender, Registration registration, Sign_Up messages){
        this.decryption = decryption;
        this.stateManager = stateManager;
        this.registration = registration;
        this.messageSender = messageSender;
        this.sign_up = messages;
    }

    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try{
            stateManager.verify(ueMessage);
            message = decryption.decryptMessage(ueMessage);
            readInfo();
            registration.register(nick, password);
            answer = sign_up.ok(authorID);
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch (DecryptingException e){
            answer = Server_error.unableToDecrypt(authorID);
        } catch (AlreadyInCollection e){
            answer = new Sign_Up().busyLogin(authorID);
        } catch (OverloadedCannotAddNew e) {
            answer = new Sign_Up().toManyAccounts(authorID);
        }

        try{
            messageSender.send(answer);
        } catch (IOException e) {
            System.out.println("Failed to send message to user (answer for sign_Up).");
        }

    }

    private void readInfo(){
        authorID = message.getAuthorID();
        String[] strings = message.getPackages().toArray(new String[0]);
        this.nick = strings[0];
        this.password = strings[1];
    }
}
