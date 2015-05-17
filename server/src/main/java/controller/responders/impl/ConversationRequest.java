package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.responders.exceptions.ToMuchUsersInThisRoom;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Conversation_Request;
import message.generators.Log_In;
import message.generators.Server_error;
import message.types.UEMessage;
import message.types.UMessage;
import model.Account;
import model.containers.LoggedUtil;
import model.containers.PublicKeysManager;
import model.containers.RoomManager;
import model.containers.permanent.Authentication;
import model.containers.permanent.BlackListUtil;
import model.exceptions.ElementNotFoundException;
import model.exceptions.IncorrectNickOrPassword;
import model.user.UserState;
import newServer.sender.MessageSender;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;

import java.io.IOException;

/**
 * Created by tochur on 17.05.15.
 */
public class ConversationRequest implements IMessageResponder {
    private Decryption decryption;
    private StateManager stateManager;
    private MessageSender messageSender;
    private BlackListUtil blackListUtil;
    private LoggedUtil loggedUtil;
    private RoomManager roomManager;
    private Conversation_Request conversation_request;

    @Inject
    public ConversationRequest(Decryption decryption, StateManager stateManager, MessageSender messageSender, BlackListUtil blackListUtil, LoggedUtil loggedUtil, RoomManager roomManager, Conversation_Request conversation_request){
        this.decryption = decryption;
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.blackListUtil = blackListUtil;
        this.loggedUtil = loggedUtil;
        this.roomManager = roomManager;
        this.conversation_request = conversation_request;
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

            //Checks weather user is logged
            otherUserID = loggedUtil.getUserId(otherUserNick);
            loggedUtil.isLogged(otherUserID);

            //Checks weather author(user that requested conversation) is not on black list
            authorNick = loggedUtil.getUserNick(authorID);
            blackListUtil.isOnBlackList(otherUserID, authorNick);

            //Checks weather user is not busy
            if ( !roomManager.isFree(otherUserID))
                throw new ToMuchUsersInThisRoom();

            //Creating connection
            roomManager.startConversation(authorID, otherUserID);

            //Informing interested people
            stateManager.update(authorID, UserState.IN_ROOM);
            stateManager.update(otherUserID, UserState.IN_ROOM);

            //creating answers (containing publicKeys)
            answer = conversation_request.connected(authorID, otherUserID, otherUserNick);
            informAboutRequest = conversation_request.connected(otherUserID, authorID, authorNick);
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch(DecryptingException e) {
            answer = Server_error.unableToDecrypt(authorID);
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        } catch (ToMuchUsersInThisRoom toMuchUsersInThisRoom) {
            toMuchUsersInThisRoom.printStackTrace();
        } catch (EncryptionException e) {
            e.printStackTrace();
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
        this.otherUserNick = strings[0];
    }

    private UEMessage ueMessage;
    private UMessage uMessage;
    private Integer authorID;
    private Integer otherUserID;
    private String authorNick;
    private String otherUserNick;
    private UEMessage answer;
    private UEMessage informAboutRequest;
}
