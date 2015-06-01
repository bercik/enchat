package message.generators;

import com.google.inject.Inject;
import model.ClientPublicKeyInfo;
import model.containers.temporary.PublicKeysManager;
import model.exceptions.ElementNotFoundException;
import rsa.RSA;
import rsa.exceptions.EncryptingException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by tochur on 18.05.15.
 */
public class KeyPackageSupplier {
    private PublicKeysManager publicKeysManager;

    @Inject
    public KeyPackageSupplier(PublicKeysManager publicKeysManager){
        this.publicKeysManager = publicKeysManager;
    }


    /**
     * Prepares info about the user that was just connected. In succeeding array fields are:
     *  [nick, modulus_I_Part], [modulus_II_Part], [exponent_I_Part], [exponent_II_Part].
     * @param keySourceID - id of the user whose public key info is sending
     * @param sourceUserNick - nick of the user whose public key info is sending
     * @return Array with info about user, necessary to lead encrypted conversation.
     * @throws ElementNotFoundException
     */
    public String[] supply(Integer keySourceID, String sourceUserNick) throws ElementNotFoundException, EncryptingException {

        ClientPublicKeyInfo publicKeyInfo = publicKeysManager.getClientPublicKeyInfo(keySourceID);
        BigInteger modulus = publicKeyInfo.getModulus();
        BigInteger exponent = publicKeyInfo.getExponent();


        String message = modulus.toString() + ";" + exponent.toString();

        String[] messageList = new String[message.length() + 1];
        messageList[0] = sourceUserNick;
        for(int i = 1; i<message.length() + 1; i++){
            messageList[i] = (String.valueOf(message.charAt(i - 1)));
        }

        return messageList;
    }
    /**
     * Prepares info about the user that was just connected. In succeeding array fields are:
     *  [nick, modulus_I_Part], [modulus_II_Part], [exponent_I_Part], [exponent_II_Part].
     * @param keySourceID - id of the user whose public key info is sending
     * @param sourceUserNick - nick of the user whose public key info is sending
     * @return Array with info about user, necessary to lead encrypted conversation.
     * @throws ElementNotFoundException
     */
    /*public String[] supply(Integer keySourceID, String sourceUserNick) throws ElementNotFoundException, EncryptingException {

        ClientPublicKeyInfo publicKeyInfo = publicKeysManager.getClientPublicKeyInfo(keySourceID);
        BigInteger modulus = publicKeyInfo.getModulus();
        BigInteger exponent = publicKeyInfo.getExponent();


        byte[] modulusByte= modulus.toByteArray();
        byte[] exponentByte= exponent.toByteArray();

        byte[] modFirst = new byte[0];
        byte[] modSecond = new byte[0];
        byte[] expFirst = new byte[0];
        byte[] expSecond = new byte[0];

        int length = modulusByte.length;
        if (length > 0){
            modFirst = Arrays.copyOfRange(modulusByte, 0, length/2);
            modSecond = Arrays.copyOfRange(modulusByte, length/2, length);
        }

        length = exponentByte.length;
        if(length > 0){
            expFirst = Arrays.copyOfRange(exponentByte, 0, length/2);
            expSecond = Arrays.copyOfRange(exponentByte, length/2, length);
        }

        String[] array = new String[5];
        array[0] = sourceUserNick;
        try{
            array[1] = new String(modFirst, RSA.STRING_CODING);
            array[2] = new String(modSecond, RSA.STRING_CODING);
            array[3] = new String(expFirst, RSA.STRING_CODING);
            array[4] = new String(expSecond, RSA.STRING_CODING);
        } catch (UnsupportedEncodingException e) {
            throw new EncryptingException();
        }


        return array;
    }*/
}
