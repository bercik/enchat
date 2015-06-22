package message.generators;

import com.google.inject.Inject;
import model.ClientPublicKeyInfo;
import model.containers.temporary.PublicKeysManager;
import model.exceptions.ElementNotFoundException;
import rsa.exceptions.EncryptingException;

import java.math.BigInteger;

/**
 * Util used to prepare PublicKey to send - divides the modulus and exponent into strings that
 * then may be wrapped into the Packs (encrypted).
 *
 * @author Created by tochur on 18.05.15.
 */
public class KeyPackageSupplier {
    private PublicKeysManager publicKeysManager;

    @Inject
    public KeyPackageSupplier(PublicKeysManager publicKeysManager){
        this.publicKeysManager = publicKeysManager;
    }


    /**
     * Prepares info about the user that was just connected. In succeeding array fields are:
     *  [nick], [modulus_in__n_Parts].... [;], [exponent_in_n_parts]...
     * @param keySourceID - id of the user whose public key info is sending
     * @param sourceUserNick - nick of the user whose public key info is sending
     * @return Array with info about user, necessary to lead encrypted conversation.
     * @throws ElementNotFoundException when there is no PublicKey associates with user.
     * @throws EncryptingException when sth went wrong with encryption process.
     */
    public String[] supply(Integer keySourceID, String sourceUserNick) throws ElementNotFoundException, EncryptingException {

        ClientPublicKeyInfo publicKeyInfo = publicKeysManager.getClientPublicKeyInfo(keySourceID);
        BigInteger modulus = publicKeyInfo.getModulus();
        BigInteger exponent = publicKeyInfo.getExponent();


        String message = modulus.toString() + ";" + exponent.toString();

        //Integer keyParts = new Double(Math.ceil((double)message.length() / 240)).intValue();
        Integer modulusParts = (int)(Math.ceil((double)modulus.toString().length() / 240));
        Integer exponentParts = (int)(Math.ceil((double)exponent.toString().length() / 240));

        String[] messageList = new String[1 + modulusParts + 1 + exponentParts];
        //Creating first message part
        int index = 0;
        messageList[index++] = sourceUserNick;

        for(int i = 0; i<modulusParts; i++) {
            int end;
            int length = modulus.toString().length();
            if((i + 1) * 240 > length)
                end = length;
            else
                end = (i + 1) * 240;
            String val = modulus.toString().substring(i * 240, end);
            messageList[index++] = val;
        }

        messageList[index++] = ";";

        for(int i = 0; i<exponentParts; i++) {
            int end;
            int length = exponent.toString().length();
            if((i + 1) * 240 > length)
                end = length;
            else
                end = (i + 1) * 240;
            String val = exponent.toString().substring(i * 240, end);
            messageList[index++] = val;
        }

        return messageList;
    }
}
