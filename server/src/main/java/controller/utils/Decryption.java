package controller.utils;

import com.google.inject.Inject;
import message.types.EncryptedMessage;
import message.types.Message;
import message.types.Pack;
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
    private PrivateKey privateServerKey;

    @Inject
    public Decryption(PrivateKey privateKey){
        this.privateServerKey = privateKey;
        this.privateServerKey = privateKey;
    }

    public Message decryptMessage(EncryptedMessage encrypted, PublicKey senderKey) throws DecryptingException{
        if( encrypted.getPackageAmount() == 0 ){
            return new Message(encrypted.getId(), encrypted.getErrorId());
        }else{
            List<Pack> packages = encrypted.getPackages();
            List<String> strings = new LinkedList<>();
            for(Pack pack: packages){
                byte[] decrypted = decrypt(pack.getDataArray());
                checkSign(pack.getSignArray(), decrypted, senderKey);
                strings.add(new String(decrypted));
            }
            return new Message(encrypted.getId(), encrypted.getErrorId(), encrypted.getPackageAmount(), strings);
        }
    }

    public byte[] decrypt(byte[] data) throws DecryptingException {
        try {
            return RSA.decrypt(data, privateServerKey);
        } catch (Exception e) {
            throw new DecryptingException();
        }
    }

    public void checkSign(byte[] sign, byte[] decrypted, PublicKey publicUserKey) throws DecryptingException {
        try {
            RSA.checkSign(sign, decrypted, publicUserKey);
        } catch (Exception e) {
            throw new DecryptingException();
        }
    }
}
