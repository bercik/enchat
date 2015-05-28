package message.generators;

import com.google.inject.Inject;
import model.ClientPublicKeyInfo;
import model.containers.temporary.PublicKeysManager;
import model.exceptions.ElementNotFoundException;

import java.math.BigInteger;

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
    public String[] supply(Integer keySourceID, String sourceUserNick) throws ElementNotFoundException {

        ClientPublicKeyInfo publicKeyInfo = publicKeysManager.getClientPublicKeyInfo(keySourceID);
        BigInteger modulus = publicKeyInfo.getModulus();
        BigInteger exponent = publicKeyInfo.getExponent();

        String mod = modulus.toString();
        String exp = exponent.toString();

        String modFirst = "";
        String modSecond = "";
        String expFirst = "";
        String expSecond = "";

        int length = mod.length();
        if (length > 0){
            modFirst = mod.substring(0, length/2);
            modSecond = mod.substring(length/2, length);
        }

        length = exp.length();
        if(length > 0){
            expFirst = exp.substring(0, length/2);
            expSecond = exp.substring(length/2, length);
        }

        String[] array = new String[5];
        array[0] = sourceUserNick;
        array[1] = modFirst;
        array[2] = modSecond;
        array[3] = expFirst;
        array[4] = expSecond;

        return array;
    }
}
