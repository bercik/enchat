package controller.utils.cypher;

import com.google.inject.Inject;
import message.types.UEMessage;
import message.types.UMessage;
import model.containers.temporary.PublicKeys;
import rsa.exceptions.EncryptionException;

import java.security.PublicKey;

/**
 * @author Created by tochur on 16.05.15.
 */
public class Encryption {
    EncryptionUtil encryptionUtil;
    PublicKeys publicKeys;

    @Inject
    public Encryption(EncryptionUtil encryptionUtil, PublicKeys publicKeys){
        this.encryptionUtil = encryptionUtil;
        this.publicKeys = publicKeys;
    }

    public UEMessage encryptMessage(UMessage uMessage) throws EncryptionException {
        Integer userID = uMessage.getAuthorID();
        PublicKey senderKey = publicKeys.getKey(userID);

        return encryptionUtil.encryptMessage(uMessage, senderKey);
    }
}
