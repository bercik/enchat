package newServer.listeners.message;

import com.google.inject.Inject;
import model.ClientInput;
import newServer.network.Server;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 18.04.15.
 *
 * This class is responsible for cheeking weather new bytes appeared in
 * any of DataInputStreams that this class holds.
 * When it happens it call MessageReader, that tries to construct Message
 * from bytes in input stream.
 */

public class InputStreamsListener implements Runnable{
    private StreamsHandler streamHandler;
    // Maps the socket references to User - the owner of the socket.
    private static Map<Integer, DataInputStream> clientsInput = new HashMap<>();
    //Buffer that holds users that will be added(in next iteration)
    private Map<Integer, DataInputStream> toAdd = new HashMap<>();
    //Buffer for ID associated with users that changes state <Add(True)/Remove(False), ID>
    private Map<Boolean, Integer> toModify = new HashMap<>();

    @Inject
    public InputStreamsListener( StreamsHandler streamHandler){
        System.out.print("INPUT STREAM LISTENER");
        this.streamHandler = streamHandler;
    }

    /* Thread function, that scan input sockets */
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
     * Adds new client input to buffer in next iteration it also will be checked
     * if no request for deleting appears
     * @param clientInput - object that represents userInput stream
     */
    public void addStreamToScan(ClientInput clientInput){
        toAdd.put(clientInput.getID(), clientInput.getInputStream());
        toModify.put(true, clientInput.getID());

    }

    public void deleteInputWithId (Integer ID){
        toModify.put(false, ID);
    }

    /**
     * Należy zablikować dodawanie (aby inny wątek nie zmodyfikował buforu
     * When ID (value) is associated with True, new input stream is added
     * otherwise the stream with ID is removing.
     */
    private void updateClientsInputs(){
        for (Boolean add: toModify.keySet()){
            int ID = toModify.get(add);
            if( add ){
                clientsInput.put(ID, toAdd.get(ID));
            }else {
                clientsInput.remove(ID);
            }

        }
    }
}
