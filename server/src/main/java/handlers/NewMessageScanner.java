package handlers;

import containers.ActiveUsers;
import user.ActiveUser;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by tochur on 18.04.15.
 *
 * It user send next message new thread is created to handle this message.
 */
public class NewMessageScanner implements Runnable{
    /* ActiveUsers (ArrayList)should be blocked*/
    public void run() {
        while (true){
            ArrayList<ActiveUser> activeUsers = ActiveUsers.getInstance().getActiveUsers();
            for(ActiveUser activeUser: activeUsers){
                if ( activeUser.isBufferFree() ){
                    activeUser.setCanCheckBuffer(false);
                    BufferHandler.handleBuffer(activeUser);
                    activeUser.setCanCheckBuffer(true);
                }

            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                System.out.println("Interuuped exception");
                ie.printStackTrace();
            }
        }
    }
}
