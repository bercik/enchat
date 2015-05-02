package message.generarators;

import message.types.EncryptedMessage;
import message.types.Message;
import message.utils.Encryption;
import messages.IncorrectMessageId;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.User;

/**
 * Created by tochur on 01.05.15.
 */
public class Clients_Message {
    public static EncryptedMessage deliveryFailed(User receiver){
        MessageId id = MessageId.CLIENT_MESSAGE;
        EncryptedMessage answer = null;
        try {
            Message message = new Message(id, id.createErrorId(1), receiver.getNick());
            answer = Encryption.encryptMessage(receiver, message);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        } catch (EncryptionException e) {
            System.out.println("Failed to encrypt error message in Clients_Message.deliveryFailed");
        }
        return answer;
    }
}
