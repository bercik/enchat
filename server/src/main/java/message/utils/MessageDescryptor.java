package message.utils;


import message.EncryptedMessage;
import message.Message;
import message.Pack;


import java.util.List;

/**
 * Created by tochur on 20.04.15.
 */
public class MessageDescryptor {
    public static Message decrypt(EncryptedMessage encryptedMessage){
        List<Pack> packs = encryptedMessage.getPackages();
        for(Pack pack: packs){
            //RSA.decrypt( pack.getDataArray(), Server.kgetInstance().getKeyContainer());
        }
        Message message;
        return null;
    }
}
