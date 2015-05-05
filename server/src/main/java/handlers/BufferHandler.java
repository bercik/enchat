package handlers;

import message.exceptions.MessageIdException;
import message.types.EncryptedMessage;
import message.utils.MessageReader;
import messages.IncorrectMessageId;
import user.User;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Cheeks if new data are available in input stream associated with user.
 * If so, Reads the message, decrypts it, and starts new (suitable) thread,
 * that is responsible for reacting for that message
 *
 * Created by tochur on 25.04.15.
 */
public class BufferHandler {

    public static void handleBuffer(User user){
        try {
            if (! isEmpty(user.getInputStream())) {
                MessageReader messageReader = new MessageReader();
                EncryptedMessage encryptedMessage = messageReader.readMessage(user);
                //Consider starting a new Thread
                NewMessageHandler newMessageHandler = new NewMessageHandler(user, encryptedMessage);
                new Thread(newMessageHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectMessageId incorrectMessageId) {
            incorrectMessageId.printStackTrace();
        } catch (MessageIdException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(DataInputStream inputStream) throws IOException {
        return inputStream.available() <= 0;
    }
}
