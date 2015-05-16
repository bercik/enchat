package controller.utils.cypher;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import message.types.*;
import rsa.RSA;
import rsa.exceptions.DecryptingException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 16.05.15.
 */

@Singleton
public class DecryptionUtil {
    PrivateKey privateServerKey;

    @Inject
    public DecryptionUtil(@Named("Server")PrivateKey privateKey){
        this.privateServerKey = privateKey;
    }

    public UMessage decryptMessage(UEMessage encrypted, PublicKey senderKey) throws DecryptingException {
        EncryptedMessage encrypt = encrypted.getEncryptedMessage();
        Message message = decryptMessage(encrypt, senderKey);
        return new UMessage(encrypted.getAuthorID(), message);
    }

    public Message decryptMessage(EncryptedMessage encrypted, PublicKey senderKey) throws DecryptingException {
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
