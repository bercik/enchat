package handlers;

import containers.ActiveUsers;
import user.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by tochur on 18.04.15.
 *
 * It user send next message new thread is created to handle this message.
 */
public class NewMessageScanner implements Runnable{
    /* ActiveUsers (ArrayList)should be blocked*/
    public void run() {
        while (true){
            Collection<User> users = ActiveUsers.getInstance().getActiveUsers();
            for(User user : users){
                if ( true ){ //check state
                    BufferHandler.handleBuffer(user);
                }

            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                System.out.println("Interuuped exceptions");
                ie.printStackTrace();
            }
        }
    }
}
