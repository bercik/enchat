package message;

import message.types.EncryptedMessage;
import rsa.RSA;
import rsa.exceptions.DecryptingException;
import user.User;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 10.05.15.
 */
public class Decryption {
    private PrivateKey privateKey;

    public Decryption(PrivateKey serverPrivateKey){
        this.privateKey = serverPrivateKey;
    }

    public byte[] decrypt(byte[] data) throws DecryptingException {
        try {
            return RSA.decrypt(data, privateKey);
        } catch (Exception e) {
            throw new DecryptingException();
        }
    }

    private void checkSign(byte[] sign, byte[] decrypted, PublicKey publicUserKey) throws DecryptingException {
        try {
            RSA.checkSign(sign, decrypted, publicUserKey);
        } catch (Exception e) {
            throw new DecryptingException();
        }
    }

    /*public Message decryptMessage(EncryptedMessage encrypted) throws DecryptingException {
        if( encrypted.getPackageAmount() == 0 ){
            return new Message(encrypted.getId(), encrypted.getErrorId());
        }else{
            List<message.types.Pack> packages = encrypted.getPackages();
            List<String> strings = new LinkedList<>();
            Key privateServerKey = getPrivateKey();
            for(message.types.Pack pack: packages){
                byte[] decrypted = decrypt(pack.getDataArray(), privateServerKey);
                checkSign(pack.getSignArray(), decrypted, user.getPublicKey());
                strings.add(new String(decrypted));
            }
            return new message.types.Message(encrypted.getId(), encrypted.getErrorId(), encrypted.getPackageAmount(), strings);
        }
    }*/
}
