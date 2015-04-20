package message;

import messages.IncorrectMessageId;
import messages.MessageId;

import java.util.ArrayList;

/**
 * Created by tochur on 16.04.15.
 */
public class Message {
    private MessageId id;
    private int errorId;
    private int packageAmount;
    private ArrayList<Pack> packages = new ArrayList<Pack>();

    public Message(int id, int errorId, int packageAmount, ArrayList<Pack> packages) throws IncorrectMessageId {
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

    public ArrayList<Pack> getPackages() {
        return packages;
    }
}
