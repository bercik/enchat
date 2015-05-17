package controller.responders.impl.lists;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.state.StateManager;
import message.generators.Black_List;
import message.generators.Server_error;
import message.types.UEMessage;
import model.containers.permanent.BlackListUtil;
import server.sender.MessageSender;
import rsa.exceptions.EncryptionException;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by tochur on 17.05.15.
 */
public class BlackList implements IMessageResponder{
    private UEMessage ueMessage;
    private UEMessage answer;
    private Integer authorID;

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
            readInfo();
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

    private void readInfo(){
        authorID = ueMessage.getAuthorID();
    }

    private StateManager stateManager;
    private MessageSender messageSender;
    private BlackListUtil blackListUtil;
    private Black_List messages;
}
