package message.utils;

import message.types.Message;
import message.types.Pack;
import message.types.EncryptedMessage;
import messages.IncorrectMessageId;
import rsa.RSA;
import server.Server;
import user.ActiveUser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import java.util.List;

/**
 * Util, that is used for encrypting and decrypting Messages.
 *
 * Created by tochur on 24.04.15.
 */
public class Encryption {
    public static Message decryptMessage(EncryptedMessage encrypted, ActiveUser activeUser) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, UnsupportedEncodingException, SignatureException {
        if( encrypted.getPackageAmount() == 0 ){
            return new Message(encrypted.getId(), encrypted.getErrorId());
        }else{
            List<Pack> packages = encrypted.getPackages();
            List<String> strings = new LinkedList<String>();
            Key privateServerKey = Server.getInstance().getKeyContainer().getPrivateKeyInfo().getPrivateKey();
            for(Pack pack: packages){
                byte[] decrypted = RSA.decrypt(pack.getDataArray(), privateServerKey);
                RSA.checkSign(pack.getSignArray(),decrypted, activeUser.getPublicKey());
                strings.add(new String(decrypted));
            }
            return new Message(encrypted.getId(), encrypted.getErrorId(), encrypted.getPackageAmount(), strings);
        }
    }

    /**
     * Only packages (data) are encrypting
     * @param activeUser - receiver of the message
     * @param message - message to encrypt
     * @return encrypted message
     */
    public static EncryptedMessage encryptMessage(ActiveUser activeUser, Message message) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IncorrectMessageId, InvalidKeySpecException, SignatureException {
        List<Pack> packages = new LinkedList<Pack>();

        if( message.getPackageAmount() == 0 ){
            return new EncryptedMessage(message.getId(), message.getErrorId());
        }else{
            int stringsAmount = message.getPackageAmount();
            if (stringsAmount > 0){
                for(String string: message.getPackages()){
                    byte[] data = string.getBytes();
                    byte[] encrypted = RSA.encrypt(data, activeUser.getPublicKey());
                    PrivateKey privateKey = Server.getInstance().getKeyContainer().getPrivateKeyInfo().getPrivateKey();
                    byte[] sign = RSA.sign(data, privateKey);

                    packages.add(new Pack(encrypted, sign));
                }
            }
            return new EncryptedMessage(message.getId(), message.getErrorId(), message.getPackageAmount(), packages);
        }
    }
}
