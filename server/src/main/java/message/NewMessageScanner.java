package message;

import containers.ActiveUsers;
import message.handlers.MessageHandler;
import message.handlers.MessageHandlerFactory;
import messages.IncorrectMessageId;
import user.ActiveUser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tochur on 18.04.15.
 *
 * It user send next message new thread is created to handle this message.
 */
public class NewMessageScanner implements Runnable{
    /* ActiveUsers (ArrayList)should be blocked*/
    @Override
    public void run() {
        while (true){
            ArrayList<ActiveUser> activeUsers = ActiveUsers.getInstance().getActiveUsers();
            for(ActiveUser activeUser: activeUsers){
                if ( activeUser.checkMessageBox() )
                    try {
                        MessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(activeUser);
                        messageHandler.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IncorrectMessageId incorrectMessageId) {
                        incorrectMessageId.printStackTrace();
                    }

            }
        }
    }
}
