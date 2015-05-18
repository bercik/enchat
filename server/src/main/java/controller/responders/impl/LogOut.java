package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Log_In;
import message.generators.Log_Out;
import message.generators.Server_error;
import message.types.UEMessage;
import message.types.UMessage;
import model.Account;
import model.containers.permanent.Authentication;
import model.exceptions.IncorrectNickOrPassword;
import model.user.UserState;
import rsa.exceptions.DecryptingException;
import server.sender.MessageSender;

import java.io.IOException;

/**
 * Created by tochur on 18.05.15.
 */
public class LogOut implements IMessageResponder {
    private StateManager stateManager;
    private MessageSender messageSender;
    private Log_Out logout;

    @Inject
    public LogOut(StateManager stateManager, MessageSender messageSender, Log_Out logout){
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.logout = logout;
    }

    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try{
            //Sender state verifier
            stateManager.verify(ueMessage);

            readInfo();

            stateManager.update(authorID, UserState.CONNECTED_TO_SERVER);
            answer = logout.logoutSuccessful(authorID);
        } catch(IncorrectUserStateException e){
            e.printStackTrace();
            System.out.println("Incorrect user state");
        }

        try{
            messageSender.send(answer);
        } catch (IOException e) {
            System.out.println("Unable to send message to user - answer for Log out request.");
        }

    }

    private void readInfo(){
        authorID = ueMessage.getAuthorID();
    }

    private UEMessage ueMessage;
    private Integer authorID;
    private UEMessage answer;
}
