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
import model.containers.permanent.blacklist.BlackListUtil;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import server.sender.MessageSender;
import rsa.exceptions.DecryptingException;

import java.io.IOException;

/**
 * @author Created by tochur on 17.05.15.
 */
public class AddToBlackList implements IMessageResponder {

    private UEMessage ueMessage;
    private UMessage uMessage;
    private UEMessage answer;
    private Integer authorID;
    private String nickToAdd;

    @Inject
    public AddToBlackList(StateManager stateManager, Decryption decryption, MessageSender messageSender, BlackListUtil blackListUtil, Black_List messages){
        this.stateManager = stateManager;
        this.decryption = decryption;
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
            uMessage = decryption.decryptMessage(ueMessage);
            readInfo();
            blackListUtil.addToBlackList(authorID, nickToAdd);
            answer = messages.addedSuccessfully(authorID);
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch(DecryptingException e) {
            answer = Server_error.unableToDecrypt(authorID);
        } catch (OverloadedCannotAddNew e) {
            answer = messages.toMuchOnList(authorID);
        } catch (AlreadyInCollection e) {
            answer = messages.alreadyAdded(authorID);
        } catch (UserNotExists e) {
            answer = messages.userNotExistsCannotAdd(authorID);
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
        this.nickToAdd = strings[0];
    }

    private StateManager stateManager;
    private Decryption decryption;
    private MessageSender messageSender;
    private BlackListUtil blackListUtil;
    private Black_List messages;
}
