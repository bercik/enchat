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
 * Lower level util, that is used for encrypting Messages.
 *
 * @author Created by tochur on 24.04.15.
 */
public class EncryptionUtil {
    private PrivateKey serverPrivateKey;

    /**
     * Creates the Encryption Util.
     * @param serverPrivateKey PrivateKey, key used to decrypt message from other users.
     */
    @Inject
    public EncryptionUtil(@Named("Server")PrivateKey serverPrivateKey){
        this.serverPrivateKey = serverPrivateKey;
    }

    /**
     * Encrypts message.
     * @param message UMessage, message to encrypt.
     * @param receiverKey PublicKey, public Key of the message receiver.
     * @return UEMessage, message ready to send.
     * @throws EncryptionException when sth went wrong during encrypting.
     */
    public UEMessage encryptMessage(UMessage message, PublicKey receiverKey) throws EncryptionException {
        Message m = message.getMessage();
        EncryptedMessage encrypted = encryptMessage(m, receiverKey);
        return new UEMessage(message.getAuthorID(), encrypted);
    }

    /**
     * Encrypts message.
     * @param message Message, message to encrypt.
     * @param receiverKey PublicKey, public Key of the message receiver.
     * @return UEMessage, message ready to send.
     * @throws EncryptionException when sth went wrong during encrypting.
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

    /**
     * Encrypts data array, using PublicKeyPassed as parameter.
     * @param data byte[], array with data.
     * @param publicUserKey PublicKey, public key of the message receiver.
     * @return byte[] byte array with encrypted message.
     * @throws EncryptingException when sth went wrong during encrypting.
     */
    public byte[] encrypt(byte[] data, PublicKey publicUserKey) throws EncryptingException {
        try {
            return RSA.encrypt(data, publicUserKey);
        } catch (Exception e) {
            throw new EncryptingException();
        }
    }

    /**
     * Creates a sign to data sent in the package.
     * @param data byte[], array to sign
     * @return byte[], byte array with sign.
     * @throws EncryptingException when sth went wrong during encrypting.
     */
    public byte[] sign(byte[] data) throws EncryptingException {
        try {
            return  RSA.sign(data, serverPrivateKey);
        } catch (Exception e) {
            throw new EncryptingException();
        }
    }
}