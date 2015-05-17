package controller.responders.impl.lists;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.responders.exceptions.UserNotExists;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Black_List;
import message.generators.Server_error;
import message.types.UEMessage;
import message.types.UMessage;
import model.containers.permanent.BlackListUtil;
import model.exceptions.AlreadyInCollection;
import model.exceptions.ElementNotFoundException;
import model.exceptions.OverloadedCannotAddNew;
import newServer.sender.MessageSender;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by tochur on 17.05.15.
 */
public class BlackList implements IMessageResponder{
    private UEMessage ueMessage;
    private UMessage uMessage;
    private UEMessage answer;
    private Integer authorID;
    private String nickToAdd;

    @Inject
    public BlackList(StateManager stateManager, MessageSender messageSender, BlackListUtil blackListUtil, Black_List messages){
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.blackListUtil = blackListUtil;
        this.messages = messages;
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
            Collection<String> userNicks = blackListUtil.getBlackList(authorID);
            answer = messages.create(authorID, userNicks.toArray(new String[0]));
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch (EncryptionException e) {
            answer = Server_error.unableToEncrypt(authorID);
        }

        try{
            messageSender.send(answer);
        } catch (IOException e) {
            System.out.println("Unable to send message to user - answer for Log In request.");
        }

    }

    private StateManager stateManager;
    private MessageSender messageSender;
    private BlackListUtil blackListUtil;
    private Black_List messages;
}
