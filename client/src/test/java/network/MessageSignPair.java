package network;

import rsa.RSA;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class MessageSignPair {
    
    //zawsze odszyfrowane
    public MessageSignPair(byte[] mmessage, byte[] ssign) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        message = Arrays.copyOf(mmessage, mmessage.length);
        sign = Arrays.copyOf(ssign, ssign.length);
    }
    
    //na tym etapie nasza wiadomość powinna być odszyfrowana
    public void checkSign(PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        RSA.checkSign(sign, message, publicKey);
    }
    
    public byte[] getMessage() {
        return Arrays.copyOf(message, message.length);
    }
    
    public byte[] getSign() {
        return Arrays.copyOf(sign, sign.length);
    }
    
    private final byte[] sign;
    private final byte[] message;
}
