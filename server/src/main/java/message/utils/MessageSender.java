package message.utils;

import message.Message;
import message.Pack;
import user.ActiveUser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tochur on 16.04.15.
 */
public class MessageSender {
    private static MessageSender messageSender;

    public static MessageSender getInstance(){
        if( messageSender == null )
            messageSender = new MessageSender();
        return messageSender;
    }

    /**
     * Sends message to the user passed as parameter
     * @param activeUser - the user to whom message is sending.
     * @param message - The sending message.
     * @throws java.io.IOException
     */
    public static void sendMessage(ActiveUser activeUser, Message message) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(activeUser.getSocket().getOutputStream());
            out.writeInt(message.getId());
            out.writeInt(message.getErrorId());
            out.writeInt(message.getPackageAmount());
            ArrayList<Pack> packs = message.getPackages();
            for (Pack pack: packs){
                out.writeInt(pack.getDataArrayLength());
                out.write(pack.getDataArray());
                out.writeInt(pack.getSignArrayLength());
                out.write(pack.getSignArray());
            }
        } finally {
            if (out != null )
                out.close();
        }
    }

    private MessageSender(){}
}
