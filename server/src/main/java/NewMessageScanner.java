import containers.ActiveUsers;
import message.utils.NewMessageHandler;
import user.ActiveUser;

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
                if ( activeUser.checkMessageBox() ) {
                    NewMessageHandler messageHandler = new NewMessageHandler(activeUser);
                    messageHandler.run();
                }

            }
        }
    }
}
