package server.sender;

import com.google.inject.Singleton;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 16.05.15.
 *
 * Associate OutputStreams with ID of user, which is unique value,
 * assigned to user during creating connection with server.
 *
 */
@Singleton
public class OutStreams {

    // Maps the client ID with it's DataOutputStream
    private Map<Integer, DataOutputStream> clientsOutput = new HashMap<>();

    /**
     * Adds new output stream associated with one user.
     * @param outputStream - new output stream
     * @param ID - client identifier associated with stream.
     */
    public void addStream(Integer ID, DataOutputStream outputStream){
        clientsOutput.put(ID, outputStream);
    }

    /**
     * Delete outStream associated with ID
     * @param ID - id associated with stream.
     */
    public void deleteStreamWithId (Integer ID){
        clientsOutput.remove(ID);
    }

    public DataOutputStream getStream(Integer ID){
        return clientsOutput.get(ID);
    }

    public int getOutputStreamsAmount(){
        return clientsOutput.size();
    }
}
