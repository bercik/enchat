package message;

import message.types.EncryptedMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by tochur on 10.05.15.
 */
public class MessageSender {

    public void sendMessage(DataOutputStream out, EncryptedMessage message) throws IOException {
        out.writeInt(message.getId().getIntRepresentation());
        out.writeInt(message.getErrorId().getIntRepresentation());
        out.writeInt(message.getPackageAmount());

        if (message.getPackageAmount() > 0){
            List<message.types.Pack> packs = message.getPackages();
            for (message.types.Pack pack: packs){
                out.writeInt(pack.getDataArrayLength());
                out.write(pack.getDataArray());
                out.writeInt(pack.getSignArrayLength());
                out.write(pack.getSignArray());
            }
        }
    }
}
