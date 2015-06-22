package message.types;

import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
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
 *
 * @author Created by tochur on 16.04.15.
 */
public class EncryptedMessage extends AbstractMessage {
    private List<Pack> packages = new LinkedList<Pack>();

    /**
     * Creates an encrypted Message.
     * @param id MessageId, id of the message
     * @param errorId ErrorID, id of the error.
     * @param packsAmount Integer, amount of packs.
     * @param packages List &lt;Pack &gt; data that will be passed in the message. In the packs (encrypted and signed).
     */
    public EncryptedMessage(MessageId id, MessageId.ErrorId errorId, int packsAmount, List<Pack> packages){
        super(id, errorId, packsAmount);
        this.packages = packages;
    }

    /**
     * Creates the message with no packs inside (just Header)
     * @param id MessageId, id of the message
     * @param errorId ErrorID, id of the error.
     */
    public EncryptedMessage(MessageId id, MessageId.ErrorId errorId){
       super(id, errorId, 0);
    }

    /**
     * Creates an encrypted Message.
     * @param header Header, header of the message.
     * @param packs List &lt;Pack &gt; data that will be passed in the message. In the packs (encrypted and signed).
     */
    public EncryptedMessage(Header header, List<Pack> packs) {
        super(header);
        this.packages = packs;
    }

    /**
     * Creates an encryptedMessage (without any packs - only header)
     * @param header Header, header of the message.
     */
    public EncryptedMessage(Header header) {
        super(header);
    }

    /**
     * Returns the List of packages that are hold in message.
     * @return List &lt;Pack &gt;, data in message (encrypted and signed).
     */
    public List<Pack> getPackages() { return packages; }
}
