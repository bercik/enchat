package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.ClientPublicKeyInfo;
import model.exceptions.ElementNotFoundException;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class PublicKeysManager {
    private PublicKeys publicKeys;

    @Inject
    public PublicKeysManager(PublicKeys publicKeys) {
        this.publicKeys = publicKeys;
    }

    public ClientPublicKeyInfo getClientPublicKeyInfo(Integer ID) throws ElementNotFoundException {
        ClientPublicKeyInfo clientPublicKeyInfo;
        if ((clientPublicKeyInfo = publicKeys.getClientPublicKeyInfo(ID)) == null)
            throw new ElementNotFoundException();
        return clientPublicKeyInfo;
    }


    public PublicKey getKey(Integer otherID) {
        System.out.println("Getting value with key: " + otherID);
        return publicKeys.getKey(otherID);
    }

    public void addKey(Integer ID, PublicKey publicKey, BigInteger modulus, BigInteger exponent){
        ClientPublicKeyInfo clientPublicKeyInfo = new ClientPublicKeyInfo(publicKey, modulus, exponent);
        publicKeys.addKey(ID, clientPublicKeyInfo);
    }
}
