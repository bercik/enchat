package server.sender;

import com.google.inject.Inject;
import message.types.UEMessage;

import java.io.DataOutputStream;
import java.io.IOException;


/**
 * Created by tochur on 13.05.15.
 *
 * Sends the UEMessage to suitable Stream.
 *  - extract userID from Message
 *  - reaches suitable stream
 *  - Call Emitter to emmit message.
 */
public class MessageSender {
    OutStreams outStreams;
    Emitter emitter;

    @Inject
    public MessageSender(OutStreams outStreams, Emitter emitter){
        this.outStreams = outStreams;
        this.emitter = emitter;
    }

    /**
     * @param ueMessage
     * @throws IOException
     */
    public void send(UEMessage ueMessage) throws IOException {
        Integer userID = ueMessage.getAuthorID();
        DataOutputStream out = outStreams.getStream(userID);
        emitter.emit(out, ueMessage.getEncryptedMessage() );
    }

}
