package message;

import containers.ActiveUsers;
import message.handlers.MessageHandlerFactory;
import messages.IncorrectMessageId;
import messages.MessageId;
import user.ActiveUser;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by tochur on 17.04.15.
 *
 * It is responsible for reading the message from the user.
 * Encrypted message from buffer is changed to Message object.
 * The message is read from the clients buffer, encrypted and Message object is created.
 */
public abstract class MessageReader{
    public abstract void readMessage(ActiveUser activeUser);

    /**
     * Read Integer from user input buffer, and creates MessageId if:
     *  - reading integer was successfully
     *  - integer has suitable value
     * @param activeUser
     * @return
     */
    public static MessageId readMessageId(ActiveUser activeUser) throws IOException, IncorrectMessageId {
        /*Reading int that represents message type*/
        DataInputStream in = new DataInputStream( activeUser.getSocket().getInputStream());
        int id = in.readInt();
        /*Creating MessageId - represents message*/
        MessageId messageId = MessageId.createMessageId(id);

        return messageId;
    }
}
