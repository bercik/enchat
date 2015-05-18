package model.containers.temporary;

import com.google.inject.Singleton;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 16.05.15.
 */
@Singleton
public class PublicKeys {
    // Maps the client ID with it's States
    private Map<Integer, PublicKey> keys = new HashMap<>();


    /**
     * Add exception
     * @param ID
     * @return
     */
    public  PublicKey getKey(Integer ID){
        return keys.get(ID);
    }


    /**
     * Add new record or replace record, that was before.
     * @param ID
     * @param publicKey
     */
    public void addKey(Integer ID, PublicKey publicKey){
        keys.put(ID, publicKey);
    }

    /**
     * Delete outStream associated with ID
     * @param ID - id associated with stream.
     */
    public void deleteRecordWithId (Integer ID){
        keys.remove(ID);
    }


    public int getRecordsAmount(){
        return keys.size();
    }

    public Map<Integer, PublicKey> getMap() {
        return keys;
    }
}
