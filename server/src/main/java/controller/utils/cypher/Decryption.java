package controller.utils.cypher;

import com.google.inject.Inject;
import message.types.*;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import rsa.exceptions.DecryptingException;

import java.security.PublicKey;

/**
 * @author Created by tochur on 15.05.15.
 */
public class Decryption {
    DecryptionUtil decryptionUtil;
    PublicKeysManager publicKeysManager;

    @Inject
    public Decryption(DecryptionUtil decryptionUtil, PublicKeysManager publicKeysManager){
        this.decryptionUtil = decryptionUtil;
        this.publicKeysManager = publicKeysManager;
    }

    public UMessage decryptMessage(UEMessage ueMessage) throws DecryptingException {
        Integer userID = ueMessage.getAuthorID();
        PublicKey senderKey = publicKeysManager.getKey(userID);

        return decryptionUtil.decryptMessage(ueMessage, senderKey);
    }
}
