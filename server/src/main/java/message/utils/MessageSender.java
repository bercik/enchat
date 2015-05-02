package message.utils;

import message.types.EncryptedMessage;
import message.types.Pack;
import user.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by tochur on 16.04.15.
 *
 * This util lets user send messages to other system components.
 */
public class MessageSender {
    /**
     * Sends message to the user passed as parameter
     * @param user - the user to whom message is sending.
     * @param message - The sending message.
     * @throws java.io.IOException
     */
    public static void sendMessage(User user, EncryptedMessage message) throws IOException {
        DataOutputStream out = user.getOutStream();
        out.writeInt(message.getId().getIntRepresentation());
        out.writeInt(message.getErrorId().getIntRepresentation());
        out.writeInt(message.getPackageAmount());

        if (message.getPackageAmount() > 0){
            List<Pack> packs = message.getPackages();
            for (Pack pack: packs){
                out.writeInt(pack.getDataArrayLength());
                out.write(pack.getDataArray());
                out.writeInt(pack.getSignArrayLength());
                out.write(pack.getSignArray());
            }
        }
    }
}
