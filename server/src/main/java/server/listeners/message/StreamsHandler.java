package server.listeners.message;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.responders.MessageRespondersFactory;
import message.exceptions.MessageIdException;
import message.types.EncryptedMessage;
import message.types.UEMessage;
import server.sender.ServerInjectorWrapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author Created by tochur on 14.05.15.
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
    ServerInjectorWrapper injectorWrapper;

    @Inject
    public StreamsHandler(StreamScanner streamScanner, MessageReader messageReader, MessageRespondersFactory factory, ServerInjectorWrapper injectorWrapper){
        this.streamScanner = streamScanner;
        this.messageReader = messageReader;
        this.factory = factory;
        this.injectorWrapper = injectorWrapper;
    }

    public void handle(Map<Integer, DataInputStream> clientsInput) {

        for(Integer ID : clientsInput.keySet()){
            DataInputStream inputStream = clientsInput.get(ID);
            try {
                if ( streamScanner.canRead(inputStream) ) {
                    EncryptedMessage encrypted = messageReader.readFromStream(inputStream);
                    UEMessage ueMessage = new UEMessage(ID, encrypted);
                    IMessageResponder responder = factory.create(encrypted.getId(), injectorWrapper.getServerInjector());
                    responder.serveEvent(ueMessage);
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
