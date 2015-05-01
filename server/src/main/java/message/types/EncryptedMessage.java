package message.types;

import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 16.04.15.
 *
 * Wraps data that can be send by the system.
 * Data are encrypted and signed.
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
