package handlers;

import message.EncryptedMessage;
import message.utils.MessageReader;
import messages.IncorrectMessageId;
import user.ActiveUser;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tochur on 25.04.15.
 */
public class BufferHandler {

    public static void handleBuffer(ActiveUser activeUser){
        try {
            if (! isEmpty(activeUser.getInputStream())) {
                MessageReader messageReader = new MessageReader();
                EncryptedMessage encryptedMessage = messageReader.readMessage(activeUser);
                //Consider starting a new Thread
                NewMessageHandler newMessageHandler = new NewMessageHandler(activeUser, encryptedMessage);
                newMessageHandler.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectMessageId incorrectMessageId) {
            incorrectMessageId.printStackTrace();
        }
    }

    public static boolean isEmpty(DataInputStream inputStream) throws IOException {
        if ( inputStream.available() > 0)
            return true;
        return false;
    }
}
