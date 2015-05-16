package newServer.listeners.message;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.MessageRespondersFactory;
import message.exceptions.MessageIdException;
import message.types.EncryptedMessage;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tochur on 14.05.15.
 *
 * This class if responsible checking weather new bytes are available in any of
 * streams that server listen to.
 * If so reads them and check weather they can be interpreted as Message.
 * If so starts new CONTROLLER thread to respond for this message.
 *  otherwise inform controller, that sth went wrong.
 */
public class StreamsHandler {
    private StreamScanner streamScanner;
    MessageReader messageReader;
    MessageRespondersFactory factory;

    @Inject
    public StreamsHandler(StreamScanner streamScanner, MessageReader messageReader, MessageRespondersFactory factory){
        this.streamScanner = streamScanner;
    }

    public void handle(Map<Integer, DataInputStream> clientsInput) {

        for(Integer ID : clientsInput.keySet()){
            DataInputStream inputStream = clientsInput.get(ID);
            try {
                if ( streamScanner.canRead(inputStream) ) {
                    EncryptedMessage encryptedMessage = messageReader.readFromStream(inputStream);
                    IMessageResponder responder = factory.create(encryptedMessage.getId());
                    new Thread(responder).start();
                }
            } catch (IOException e) {
                //Send info to controller, that failed to read.
                e.printStackTrace();
            } catch (MessageIdException e) {
                //Send to controller, that message was wrong.
                e.printStackTrace();
            }

        }

    }
}
