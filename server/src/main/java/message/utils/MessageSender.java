package message.utils;

import message.EncryptedMessage;
import message.Pack;
import user.ActiveUser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by tochur on 16.04.15.
 *
 * This util lets user send messages to other system components.
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
    public static void sendMessage(ActiveUser activeUser, EncryptedMessage message) throws IOException {
        DataOutputStream out = activeUser.getOutStream();
        out.writeInt(message.getId().getIntRepresentation());
        out.writeInt(message.getErrorId());
        out.writeInt(message.getPackageAmount());
        List<Pack> packs = message.getPackages();
        for (Pack pack: packs){
            out.writeInt(pack.getDataArrayLength());
            out.write(pack.getDataArray());
            out.writeInt(pack.getSignArrayLength());
            out.write(pack.getSignArray());
        }
    }

    private MessageSender(){}
}
