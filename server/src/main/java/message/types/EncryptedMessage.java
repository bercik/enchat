package message.types;

import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 16.04.15.
 *
 * Wraps data that can be send by the system.
 * Data are encrypted and signed.
 *  * It is responsible for reading the message from the stream.
 * Message from buffer is changed to EncryptedMessage object.
 * The message is read from the clients buffer.
 * Message format is:
 *      id      - (int32)
 *      errorId  - (int32)
 *      packageAmount - (int32)
 *      dataArrayLength - (int32)
 *      dataArray       - ( byte[byteArrayLength] )
 *      signArrayLength - (int32)
 *      signArray       - ( byte[signArrayLength] )
 *
 */
public class EncryptedMessage extends AbstractMessage {
    private List<Pack> packages = new LinkedList<Pack>();


    public EncryptedMessage(MessageId id, MessageId.ErrorId errorId, int packsAmount, List<Pack> packages){
        super(id, errorId, packsAmount);
        this.packages = packages;
    }

    /*Message with no packs*/
    public EncryptedMessage(MessageId id, MessageId.ErrorId errorId){
       super(id, errorId, 0);
    }

    public EncryptedMessage(Header header, List<Pack> packs) {
        super(header);
        this.packages = packs;
    }

    public EncryptedMessage(Header header) {
        super(header);
    }

    public List<Pack> getPackages() { return packages; }
}
