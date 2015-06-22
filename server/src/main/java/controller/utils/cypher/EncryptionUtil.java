package controller.utils.cypher;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import message.types.*;
import rsa.RSA;
import rsa.exceptions.EncryptingException;
import rsa.exceptions.EncryptionException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;

/**
 * Util, that is used for encrypting and decrypting Messages.
 *
 * @author Created by tochur on 24.04.15.
 */
public class EncryptionUtil {
    private PrivateKey serverPrivateKey;

    @Inject
    public EncryptionUtil(@Named("Server")PrivateKey serverPrivateKey){
        this.serverPrivateKey = serverPrivateKey;
    }

    public UEMessage encryptMessage(UMessage message, PublicKey receiverKey) throws EncryptionException {
        Message m = message.getMessage();
        EncryptedMessage encrypted = encryptMessage(m, receiverKey);
        return new UEMessage(message.getAuthorID(), encrypted);
    }

    /**
     * Only packages (data) are encrypting
     * @return encrypted message
     */
    public EncryptedMessage encryptMessage(Message message, PublicKey receiverKey) throws EncryptionException {
        List<Pack> packages = new LinkedList<>();

        if( message.getPackageAmount() == 0 ){
            return new EncryptedMessage(message.getId(), message.getErrorId());
        }else{
            int stringsAmount = message.getPackageAmount();
            if (stringsAmount > 0){
                for(String string: message.getPackages()){
                    byte[] data = string.getBytes();
                    byte[] encrypted = encrypt(data, receiverKey);
                    byte[] sign = sign(data);

                    packages.add(new Pack(encrypted, sign));
                }
            }
            return new EncryptedMessage(message.getId(), message.getErrorId(), message.getPackageAmount(), packages);
        }
    }

    public byte[] encrypt(byte[] data, PublicKey publicUserKey) throws EncryptingException {
        try {
            return RSA.encrypt(data, publicUserKey);
        } catch (Exception e) {
            throw new EncryptingException();
        }
    }

    public byte[] sign(byte[] data) throws EncryptingException {
        try {
            return  RSA.sign(data, serverPrivateKey);
        } catch (Exception e) {
            throw new EncryptingException();
        }
    }
}