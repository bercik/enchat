package server.sender;

import com.google.inject.Inject;
import message.types.UEMessage;

import java.io.DataOutputStream;
import java.io.IOException;


/**
 * Sends the UEMessage to suitable Stream.
 *  - extract userID from Message
 *  - reaches suitable stream
 *  - Call Emitter to emmit message.
 *
 * @author Created by tochur on 13.05.15.
 */
public class MessageSender {
    OutStreams outStreams;
    Emitter emitter;

    /**
     * Creates MessageSender
     * @param outStreams OutStreams, object that holds outStreams associated with users ids.
     * @param emitter Emitter, util used to put the message to stream.
     */
    @Inject
    public MessageSender(OutStreams outStreams, Emitter emitter){
        this.outStreams = outStreams;
        this.emitter = emitter;
    }

    /**
     *  Sends the UEMessage to suitable Stream:
     *  <br> - extract userID from Message
     *  <br> - reaches suitable stream
     *  <br> - Call Emitter to emmit message.
     * @param ueMessage - message to send with id of the destination user.
     * @throws IOException - when sth went wrong during putting message into stream.
     */
    public void send(UEMessage ueMessage) throws IOException {
        Integer userID = ueMessage.getAuthorID();
        DataOutputStream out = outStreams.getStream(userID);
        emitter.emit(out, ueMessage.getEncryptedMessage() );
    }

}
