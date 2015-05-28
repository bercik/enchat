package controller.responders.impl.lists;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.state.StateManager;
import message.generators.Logged_List;
import message.generators.Server_error;
import message.types.UEMessage;
import model.containers.permanent.blacklist.BlackListUtil;
import model.containers.temporary.LoggedUtil;
import model.exceptions.ElementNotFoundException;
import server.sender.MessageSender;
import rsa.exceptions.EncryptionException;

import java.io.IOException;
import java.util.*;

/**
 * Created by tochur on 17.05.15.
 */
public class ClientList implements IMessageResponder{
    private UEMessage ueMessage;
    private UEMessage answer;
    private Integer authorID;
    private BlackListUtil blackListUtil;

    @Inject
    public ClientList(StateManager stateManager, MessageSender messageSender, LoggedUtil loggedManager, Logged_List messages, BlackListUtil blackListUtil){
        this.stateManager = stateManager;
        this.messageSender = messageSender;
        this.loggedManager = loggedManager;
        this.messages = messages;
        this.blackListUtil = blackListUtil;
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

            //Creating List of nicks That are logged
            List<String> toShow = prepareListToSend();

            //Creates the message ready to send (with authorID and encrypted)
            answer = messages.create(authorID, toShow.toArray(new String[0]));
        } catch(IncorrectUserStateException e){
            //Do nothing just ignore the message
        } catch (EncryptionException e) {
            answer = Server_error.unableToEncrypt(authorID);
        }

        try{
            messageSender.send(answer);
        } catch (IOException e) {
            System.out.println("Unable to send message to user - answer for Client List request.");
        }

    }

    /* Prepares the sorted list of user that are currently logged excluding the nick of
    * user, that request the info about logged message (author ID)
    * */
    public List<String> prepareListToSend(){
        Set<String> toShow = new HashSet<>();
        String authorNick = null;
        try {
            authorNick = loggedManager.getUserNick(authorID);
        } catch (ElementNotFoundException e) {
            //It cannot happened //user should not be allowed to send message when is not logged.
            e.printStackTrace();
        }
        for(Integer id: loggedManager.getIDs()){
            try {
                if (!blackListUtil.isOnBlackList(id,authorNick))
                    toShow.add(loggedManager.getUserNick(id));
            } catch (ElementNotFoundException e) {
                continue;
            }
        }
        toShow.remove(authorNick);
        List<String> toShowList = new LinkedList<>(toShow);

        Collections.sort(toShowList);

        return toShowList;
    }

    private void readInfo(){
        authorID = ueMessage.getAuthorID();
    }

    private StateManager stateManager;
    private MessageSender messageSender;
    private LoggedUtil loggedManager;
    private Logged_List messages;

}
