package controller.utils.cypher;

import com.google.inject.Inject;
import message.types.*;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import rsa.exceptions.DecryptingException;

import java.security.PublicKey;

/**
 * High level util used to decrypt messages.
 *
 * @author Created by tochur on 15.05.15.
 */
public class Decryption {
    DecryptionUtil decryptionUtil;
    PublicKeysManager publicKeysManager;

    /**
     * Creates util used to decrypt messages.
     * @param decryptionUtil DecryptionUtil, lower level util that decrypts messages.
     * @param publicKeysManager PublicKeysManager, util that facilitates users PublicKeys.
     */
    @Inject
    public Decryption(DecryptionUtil decryptionUtil, PublicKeysManager publicKeysManager){
        this.decryptionUtil = decryptionUtil;
        this.publicKeysManager = publicKeysManager;
    }

    /**
     * Decrypts the message
     * @param ueMessage UEMessage, message to decrypt.
     * @return UMessage, decrypted message.
     * @throws DecryptingException when system was not able to decrypt message.
     */
    public UMessage decryptMessage(UEMessage ueMessage) throws DecryptingException {
        Integer userID = ueMessage.getAuthorID();
        PublicKey senderKey = publicKeysManager.getKey(userID);

        return decryptionUtil.decryptMessage(ueMessage, senderKey);
    }
}
