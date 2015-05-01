package message.utils;

import message.types.EncryptedMessage;
import message.types.Message;
import message.types.Pack;
import rsa.RSA;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptingException;
import rsa.exceptions.EncryptionException;
import server.Server;
import user.ActiveUser;

import java.security.Key;
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
    public static Message decryptMessage(EncryptedMessage encrypted, ActiveUser activeUser) throws DecryptingException{
        if( encrypted.getPackageAmount() == 0 ){
            return new Message(encrypted.getId(), encrypted.getErrorId());
        }else{
            List<Pack> packages = encrypted.getPackages();
            List<String> strings = new LinkedList<>();
            Key privateServerKey = getPrivateKey();
            for(Pack pack: packages){
                byte[] decrypted = decrypt(pack.getDataArray(), privateServerKey);
                checkSign(pack.getSignArray(), decrypted, activeUser.getPublicKey());
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
    public static EncryptedMessage encryptMessage(ActiveUser activeUser, Message message) throws EncryptionException {
        List<Pack> packages = new LinkedList<>();

        if( message.getPackageAmount() == 0 ){
            return new EncryptedMessage(message.getId(), message.getErrorId());
        }else{
            int stringsAmount = message.getPackageAmount();
            if (stringsAmount > 0){
                for(String string: message.getPackages()){
                    byte[] data = string.getBytes();
                    byte[] encrypted = encrypt(data, activeUser.getPublicKey());
                    byte[] sign = sign(data);

                    packages.add(new Pack(encrypted, sign));
                }
            }
            return new EncryptedMessage(message.getId(), message.getErrorId(), message.getPackageAmount(), packages);
        }
    }

    private static PrivateKey getPrivateKey() throws DecryptingException {
        try {
            return Server.getInstance().getKeyContainer().getPrivateKeyInfo().getPrivateKey();
        } catch (Exception e) {
            throw new DecryptingException();
        }
    }

    private static byte[] sign(byte[] data) throws EncryptingException {
        try {
            return  RSA.sign(data, getPrivateKey());
        } catch (Exception e) {
            throw new EncryptingException();
        }
    }

    private static byte[] decrypt(byte[] data, Key privateKey) throws DecryptingException {
        try {
            return RSA.decrypt(data, privateKey);
        } catch (Exception e) {
            throw new DecryptingException();
        }
    }

    private static byte[] encrypt(byte[] data, PublicKey publicUserKey) throws EncryptingException {
        try {
            return RSA.encrypt(data, publicUserKey);
        } catch (Exception e) {
            throw new EncryptingException();
        }
    }

    private static void checkSign(byte[] sign, byte[] decrypted, PublicKey publicUserKey) throws DecryptingException {
        try {
            RSA.checkSign(sign, decrypted, publicUserKey);
        } catch (Exception e) {
            throw new DecryptingException();
        }
    }
}
