package model.containers.temporary;

import com.google.inject.Singleton;
import model.ClientPublicKeyInfo;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 16.05.15.
 */
@Singleton
public class PublicKeys {
    // Maps the client ID with it's States
    private Map<Integer, ClientPublicKeyInfo> keys = new HashMap<>();


    /**
     * Add exception
     * @param ID
     * @return
     */
    public  PublicKey getKey(Integer ID){
        return keys.get(ID).getPublicKey();
    }

    public ClientPublicKeyInfo getClientPublicKeyInfo(Integer ID){
        return keys.get(ID);
    }

    /**
     *
     * @param ID
     * @param clientPublicKeyInfo
     */
    public void addKey(Integer ID, ClientPublicKeyInfo clientPublicKeyInfo){
        System.out.println("Adding value with id: " + ID);
        keys.put(ID, clientPublicKeyInfo);
    }

    /**
     *
     * @return
     */
    public Map<Integer, ClientPublicKeyInfo> getMap() {
        return keys;
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
}
