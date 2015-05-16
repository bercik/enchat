package controller.utils.cypher;

import com.google.inject.Inject;
import message.types.*;
import model.containers.PublicKeys;
import rsa.RSA;
import rsa.exceptions.DecryptingException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 15.05.15.
 */
public class Decryption {
    DecryptionUtil decryptionUtil;
    PublicKeys publicKeys;

    @Inject
    public Decryption(DecryptionUtil decryptionUtil, PublicKeys publicKeys){
        this.decryptionUtil = decryptionUtil;
        this.publicKeys = publicKeys;
    }

    public UMessage decryptMessage(UEMessage ueMessage) throws DecryptingException {
        EncryptedMessage encrypted = ueMessage.getEncryptedMessage();
        Integer userID = ueMessage.getAuthorID();
        PublicKey senderKey = publicKeys.getKey(userID);

        return decryptionUtil.decryptMessage(ueMessage, senderKey);
    }
}
