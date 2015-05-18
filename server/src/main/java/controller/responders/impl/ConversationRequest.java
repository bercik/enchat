package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.responders.exceptions.OnBlackList;
import controller.responders.exceptions.ToMuchUsersInThisRoom;
import controller.responders.exceptions.UserNotLogged;
import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Conversation_Request;
import message.generators.Incoming_Conversation;
import message.generators.Server_error;
import message.types.UEMessage;
import message.types.UMessage;
import model.containers.temporary.LoggedUtil;
import model.containers.temporary.RoomManager;
import model.containers.permanent.blacklist.BlackListUtil;
import model.exceptions.ElementNotFoundException;
import model.user.UserState;
import server.sender.MessageSender;
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
    private Incoming_Conversation incoming_conversation;

    @Inject
    public ConversationRequest(Decryption decryption, StateManager stateManager, MessageSender messageSender, BlackListUtil blackListUtil, LoggedUtil loggedUtil, RoomManager roomManager, Conversation_Request conversation_request, Incoming_Conversation incoming_conversation){
        this.decryption = decryption;
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.blackListUtil = blackListUtil;
        this.loggedUtil = loggedUtil;
        this.roomManager = roomManager;
        this.conversation_request = conversation_request;
        this.incoming_conversation = incoming_conversation;
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

            //Reads the nick of user to connect.
            readInfo();

            //Checks weather user is logged
            otherUserID = loggedUtil.getUserId(otherUserNick);
            if (!loggedUtil.isLogged(otherUserID))
                throw new UserNotLogged();

            //Checks weather author(user that requested conversation) is not on black list
            authorNick = loggedUtil.getUserNick(authorID);
            if( blackListUtil.isOnBlackList(otherUserID, authorNick))
                throw new OnBlackList();

            //Checks weather user is not busy
            if ( !roomManager.isFree(otherUserID))
                throw new ToMuchUsersInThisRoom();

            //Creating connection
            roomManager.startConversation(authorID, otherUserID);

            //State Change
            stateManager.update(authorID, UserState.IN_ROOM);
            stateManager.update(otherUserID, UserState.IN_ROOM);

            //Informing interested people creating answers (containing publicKeys)
            answer = conversation_request.connected(authorID, otherUserID, otherUserNick);
            informationAboutRequest = conversation_request.connected(otherUserID, authorID, authorNick);
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch(DecryptingException e) {
            answer = Server_error.unableToDecrypt(authorID);
        } catch (ElementNotFoundException e) {
            answer = conversation_request.notLogged(authorID);
        } catch (ToMuchUsersInThisRoom toMuchUsersInThisRoom) {
            answer = conversation_request.busyUser(authorID);
            informationAboutRequest = incoming_conversation.youWereBusy(otherUserID, authorNick);
        } catch (EncryptionException e) {
            answer = Server_error.unableToEncrypt(authorID);
        } catch (UserNotLogged userNotLogged) {
            answer = conversation_request.notLogged(authorID);
        } catch (OnBlackList onBlackList) {
            answer = conversation_request.onBlackList(authorID);
        }

        try{
            messageSender.send(answer);
            if (informationAboutRequest != null)
                messageSender.send(informationAboutRequest);
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
    private UEMessage informationAboutRequest;
}
