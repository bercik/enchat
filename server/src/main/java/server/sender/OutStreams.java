package server.sender;

import com.google.inject.Singleton;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Associate OutputStreams with ID of user, which is unique value,
 * assigned to user during creating connection with server.
 *
 * @author Created by tochur on 16.05.15.
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

    /**
     * Returns the DataOutputStream associated with the user with specified id.
     * @param ID Integer, id of the user whose Stream we want to get.
     * @return DataOutputStream, output stream - connection to the user with id passed as parameter.
     */
    public DataOutputStream getStream(Integer ID){
        return clientsOutput.get(ID);
    }

    /**
     * Returns the amount of Streams hold in this container.
     * @return amount of Streams in this container.
     */
    public int getOutputStreamsAmount(){
        return clientsOutput.size();
    }
}
