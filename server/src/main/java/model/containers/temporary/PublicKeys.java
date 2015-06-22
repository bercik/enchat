package model.containers.temporary;

import com.google.inject.Singleton;
import model.ClientPublicKeyInfo;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds Public keys of all clients that are connected to the server.
 *
 * @author Created by tochur on 16.05.15.
 */
@Singleton
public class PublicKeys {
    // Maps the client ID with it's States
    private Map<Integer, ClientPublicKeyInfo> keys = new HashMap<>();


    /**
     * Returns the PublicKey of the user specified by parameter.
     * @param ID Integer, id associated with user.
     * @return PublicKey, objects that holds user PublicKey.
     */
    public  PublicKey getKey(Integer ID){
        return keys.get(ID).getPublicKey();
    }

    /**
     * Returns the ClientPublicKey of the user specified by parameter.
     * @param ID Integer, id associated with user.
     * @return ClientPublicKey, objects that holds user ClientPublicKey.
     */
    public ClientPublicKeyInfo getClientPublicKeyInfo(Integer ID){
        return keys.get(ID);
    }

    /**
     * Adds new association userId to PublicKeyInfo
     * @param ID Integer, userID
     * @param clientPublicKeyInfo ClientPublicKeyInfo, object that holds PublicKeyInfo data.
     */
    public void addKey(Integer ID, ClientPublicKeyInfo clientPublicKeyInfo){
        System.out.println("Adding value with id: " + ID);
        keys.put(ID, clientPublicKeyInfo);
    }

    /**
     * Returns the userId to PublicKey map.
     * @return userId to PublicKey map.
     */
    public Map<Integer, ClientPublicKeyInfo> getMap() {
        return keys;
    }

    /**
     * Delete outStream associated with ID
     * @param ID Integer, id associated with stream.
     */
    public void deleteRecordWithId (Integer ID){
        keys.remove(ID);
    }


    /**
     * Returns the amount of PublicKeys (amount of connected with server users)
     * @return amount of public keys hold in the PublicKeys object.
     */
    public int getRecordsAmount(){
        return keys.size();
    }
}
