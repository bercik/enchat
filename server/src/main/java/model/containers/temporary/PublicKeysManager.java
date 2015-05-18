package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.name.Named;

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


    public PublicKey getKey(Integer otherID) {
        return publicKeys.getKey(otherID);
    }

    public void addKey(Integer ID, PublicKey publicKey){ publicKeys.addKey(ID, publicKey); }
}
