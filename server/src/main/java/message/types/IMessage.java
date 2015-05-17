package message.types;

import messages.MessageId;

/**
 * Created by tochur on 24.04.15.
 */
public interface IMessage {
    public MessageId getId();
    public MessageId.ErrorId getErrorId();
    public int getPackageAmount();
}
