package server.listeners.message;

import com.google.inject.Inject;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 18.04.15.
 *
 * The only entrance for incoming messages from connected users.
 *  (It's like the panel, available buttons - messages.)
 * This class is responsible for cheeking weather new bytes appeared in
 * any of DataInputStreams that this class holds.
 * When it happens it call MessageReader, that tries to construct Message
 * from bytes from input stream.
 */

public class InputStreamsHandler implements Runnable{
    //Util For handling the input streams
    private StreamsHandler streamHandler;

    // Maps the client ID with it's DataInputStream
    private Map<Integer, DataInputStream> clientsInput = new HashMap<>();
    //Buffer that holds users that will be added(in next iteration)
    private Map<Integer, DataInputStream> toAdd = new HashMap<>();
    //Buffer for ID associated with users that changes state <Add(True)/Remove(False), ID>
    private Map<Integer, Boolean> toModify = new HashMap<>();

    /**
     * @param streamHandler this class is responsible for:
     *                      - checking weather new bytes are available in input stream
     *                      - if so, takes responsibility for passing that info to controller
     *                      or for handling exceptions.
     */
    @Inject
    public InputStreamsHandler(StreamsHandler streamHandler){
        System.out.println("INPUT STREAMS HANDLER CONSTRUCTOR");
        this.streamHandler = streamHandler;
    }

    /**
     * Delegate, to StreamHandler, responsibility of scanning and managing the new messages
     * from all input streams that currently ale in clientsInput Map.
     * After every iteration updates list of clientsInput.
     */
    public void run() {
        while (true){
            /* Iterate through all streams */
            streamHandler.handle(clientsInput);

            //Empty buffers
            updateClientsInputs();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                System.out.println("Interrupted Exception");
                ie.printStackTrace();
            }
        }
    }

    /**
     * Add to operation buffer - new operation - adding new inputStream
     * In next iteration stream will also be checked,
     * if no request for deleting appears after this request.
     * @param inputStream - new input stream to scan.
     * @param ID - client identifier associated with stream.
     */
    public void addStreamToScan(Integer ID, DataInputStream inputStream){
        toAdd.put(ID, inputStream);
        toModify.put(ID, true);
    }

    /**
     * Add to operation buffer - new operation - deleting InputStream of user
     * signed with ID passed as parameter.
     * @param ID - id associated with user.
     */
    public void deleteInputWithId (Integer ID){
        toModify.put(ID, false);
    }

    /**
     * Należy zablikować dodawanie (aby inny wątek nie zmodyfikował buforu
     * When ID (value) is associated with True, new input stream is added
     * otherwise the stream with ID is removing.
     */
    protected void updateClientsInputs(){
        for (Integer ID: toModify.keySet()){
            Boolean add = toModify.get(ID);
            if( add ){
                clientsInput.put(ID, toAdd.get(ID));
            }else {
                clientsInput.remove(ID);
            }
        }
        toModify.clear();
        toAdd.clear();
    }

    public int getScannedStreamsAmount(){
        return clientsInput.size();
    }
}
