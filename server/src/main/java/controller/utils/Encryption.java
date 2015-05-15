package controller.utils;

import com.google.inject.Inject;
import message.types.EncryptedMessage;
import message.types.Message;
import message.types.Pack;
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
 * Created by tochur on 24.04.15.
 */
public class Encryption {
    private PrivateKey serverPrivateKey;

    @Inject
    public Encryption(PrivateKey serverPrivateKey){
        this.serverPrivateKey = serverPrivateKey;
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