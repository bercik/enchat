package controller.utils.cypher;

import com.google.inject.Inject;
import message.types.UEMessage;
import message.types.UMessage;
import model.containers.temporary.PublicKeys;
import rsa.exceptions.EncryptionException;

import java.security.PublicKey;

/**
 * High level util that encrypts messages.
 *
 * @author Created by tochur on 16.05.15.
 */
public class Encryption {
    EncryptionUtil encryptionUtil;
    PublicKeys publicKeys;

    /**
     * Creates the util used to encrypt messages.
     * @param encryptionUtil EncryptionUtil,util that encrypts messages.
     * @param publicKeys PublicKeys, holds info about users PublicKeys.
     */
    @Inject
    public Encryption(EncryptionUtil encryptionUtil, PublicKeys publicKeys){
        this.encryptionUtil = encryptionUtil;
        this.publicKeys = publicKeys;
    }

    /**
     * Encrypts message
     * @param uMessage UMessage, message to encrypt with receiver identifier (U)Message.
     * @return UEMessage, encrypted message ready to send.
     * @throws EncryptionException , when encryption failed.
     */
    public UEMessage encryptMessage(UMessage uMessage) throws EncryptionException {
        Integer userID = uMessage.getAuthorID();
        PublicKey senderKey = publicKeys.getKey(userID);

        return encryptionUtil.encryptMessage(uMessage, senderKey);
    }
}
