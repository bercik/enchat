package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Server_error;
import message.generators.Sign_Up;
import message.types.UEMessage;
import message.types.UMessage;
import model.containers.permanent.Registration;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import server.sender.MessageSender;
import rsa.exceptions.DecryptingException;

import java.io.IOException;

/**
 * Responder, that handles SignUP messages.
 *
 * @author Created by tochur on 15.05.15.
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

    /**
     * Creates Responder, that handles SignUp messages.
     * @param decryption Decryption, util used to Decrypt messages.
     * @param stateManager StateManager, util used to control users UserStates.
     * @param messageSender MessageSender, util used to send prepared message (UEMessages).
     * @param registration Registration, util used to manage registration process.
     * @param messages Sign_Up, util used to easily creation of all types of message with id Sign_Up.
     */
    @Inject
    public SignUp(Decryption decryption, StateManager stateManager, MessageSender messageSender, Registration registration, Sign_Up messages){
        this.decryption = decryption;
        this.stateManager = stateManager;
        this.registration = registration;
        this.messageSender = messageSender;
        this.sign_up = messages;
    }

    /**
     * Starts the responder as a Thread.
     * @param ueMessage UEMessage, message that will be handled by responder.
     */
    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    /**
     * Function that calls actions on utils passed
     */
    @Override
    public void run() {
        try{
            //Checking user state
            stateManager.verify(ueMessage);
            //Preparing message
            message = decryption.decryptMessage(ueMessage);
            //getting info from message
            readInfo();
            registration.register(nick, password);
            answer = sign_up.ok(authorID);
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
            System.out.print("Incorrect state");
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
        System.out.println("Author ID: " + authorID);
        String[] strings = message.getPackages().toArray(new String[0]);
        this.nick = strings[0];
        this.password = strings[1];
        System.out.print("nick: " + this.nick);
        System.out.print("password: " + this.password);
    }
}
