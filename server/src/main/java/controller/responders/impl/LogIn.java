package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Log_In;
import message.generators.Server_error;
import message.types.UEMessage;
import message.types.UMessage;
import model.Account;
import model.user.UserState;
import model.containers.permanent.Authentication;
import model.exceptions.IncorrectNickOrPassword;
import newServer.sender.MessageSender;
import rsa.exceptions.DecryptingException;

import java.io.IOException;

/**
 * Created by tochur on 16.05.15.
 */
public class LogIn implements IMessageResponder {
    private Decryption decryption;
    private StateManager stateManager;
    private MessageSender messageSender;
    private Log_In log_in;
    private Authentication authentication;

    @Inject
    public LogIn(Decryption decryption, StateManager stateManager,Authentication authentication, MessageSender messageSender, Log_In messages){
        this.decryption = decryption;
        this.stateManager = stateManager;
        this.authentication = authentication;
        this.messageSender = messageSender;
        this.log_in = messages;
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
            uMessage = decryption.decryptMessage(ueMessage);
            readInfo();
            //May save info.
            account = authentication.authenticate(nick, password);
            stateManager.update(authorID, UserState.LOGGED);
            answer = log_in.loggedSuccessfully(authorID);
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch(DecryptingException e) {
            answer = Server_error.unableToDecrypt(authorID);
        } catch (IncorrectNickOrPassword e) {
            answer = new Log_In().badLoginOrPassword(authorID);
        }

        try{
            messageSender.send(answer);
        } catch (IOException e) {
            System.out.println("Unable to send message to user - answer for Log In request.");
        }

    }

    private void readInfo(){
        authorID = uMessage.getAuthorID();
        String[] strings = uMessage.getPackages().toArray(new String[0]);
        this.nick = strings[0];
        this.password = strings[1];
    }

    private UEMessage ueMessage;
    private UMessage uMessage;
    private Integer authorID;
    private String nick;
    private String password;
    private UEMessage answer;
    private Account account;
}
