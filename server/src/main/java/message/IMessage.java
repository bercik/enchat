package message;

import messages.MessageId;

import java.util.List;

/**
 * Created by tochur on 24.04.15.
 */
public interface IMessage {
    public MessageId getId();
    public int getErrorId();
    public int getPackageAmount();
    public List<?> getPackages();
}
