package model.containers.temporary;

import com.google.inject.Inject;
import model.ClientPublicKeyInfo;
import model.exceptions.ElementNotFoundException;

import java.math.BigInteger;
import java.security.PublicKey;

/**
 * Controller for public keys management.
 *
 * @author Created by tochur on 17.05.15.
 */
public class PublicKeysManager {
    private PublicKeys publicKeys;

    /**
     * Creates new util class responsible for publicKey managing.
     * @param publicKeys PublicKeys, object that holds all info.
     */
    @Inject
    public PublicKeysManager(PublicKeys publicKeys) {
        this.publicKeys = publicKeys;
    }

    /**
     * Returns the ClientPublicKeyInfo associated with the specified user.
     * @param ID Integer, id of the user whose ClientPublicKeyInfo is accessed.
     * @return ClientPublicKeyInfo, clientPublicKeyInfo of the user with id.
     * @throws ElementNotFoundException when no account is associated with specified id.
     */
    public ClientPublicKeyInfo getClientPublicKeyInfo(Integer ID) throws ElementNotFoundException {
        ClientPublicKeyInfo clientPublicKeyInfo;
        if ((clientPublicKeyInfo = publicKeys.getClientPublicKeyInfo(ID)) == null)
            throw new ElementNotFoundException();
        return clientPublicKeyInfo;
    }


    /**
     * Returns the PublicKey associated with the specified user.
     * @param id Integer, id of the user whose PublicKey is accessed.
     * @return PublicKey, publicKey of the user with id.
     */
    public PublicKey getKey(Integer id) {
        System.out.println("Getting value with key: " + id);
        return publicKeys.getKey(id);
    }

    /**
     * Adds new key to PublicKeys
     * @param ID Integer, id of the key owner
     * @param publicKey PublicKey, public key object
     * @param modulus BigInteger, part of the key.
     * @param exponent BigInteger, part of the key.
     */
    public void addKey(Integer ID, PublicKey publicKey, BigInteger modulus, BigInteger exponent){
        ClientPublicKeyInfo clientPublicKeyInfo = new ClientPublicKeyInfo(publicKey, modulus, exponent);
        publicKeys.addKey(ID, clientPublicKeyInfo);
    }
}
