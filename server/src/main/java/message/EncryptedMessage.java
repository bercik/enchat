package message;

import messages.IncorrectMessageId;
import messages.MessageId;
import user.ActiveUser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 16.04.15.
 *
 * Wraps data that can be send by the system.
 * Data are encrypted and signed.
 */
public class EncryptedMessage implements IMessage {
    private MessageId id;
    private int errorId;
    private int packageAmount;
    private List<Pack> packages = new LinkedList<Pack>();

    public EncryptedMessage(int id, int errorId, int packageAmount, List<Pack> packages) throws IncorrectMessageId {
        this.id = MessageId.createMessageId(id);
        this.errorId = errorId;
        this.packageAmount = packageAmount;
        this.packages = packages;
    }

    public MessageId getId() {
        return id;
    }

    public int getErrorId() {
        return errorId;
    }

    public int getPackageAmount() {
        return packageAmount;
    }

    public List<Pack> getPackages() {
        return packages;
    }
}
