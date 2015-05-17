package model.containers;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.security.PublicKey;
import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class PublicKeysManager {
    private Map<Integer, PublicKey> IDKeys;

    @Inject
    public PublicKeysManager(@Named("IDAccounts") Map<Integer, PublicKey> IDKeys) {
        this.IDKeys = IDKeys;
    }


    public PublicKey getKey(Integer otherID) {
        return IDKeys.get(otherID);
    }
}
