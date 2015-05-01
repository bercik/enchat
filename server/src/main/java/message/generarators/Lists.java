package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Message;
import message.utils.Encryption;
import messages.IncorrectMessageId;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.ActiveUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tochur on 01.05.15.
 */
public class Lists {

    /**
     * Returns encrypted message with black list containing one nick in one package.
     * If encrypting failed, ServerError message is send to user.
     * @param receiver - the ActiveUser that request for his blackList
     * @param nicks - nicks from blackList
     * @return - encryptedMessage
     */
    public static EncryptedMessage blackList(ActiveUser receiver, String[] nicks){
        MessageId id = MessageId.BLACK_LIST;
        EncryptedMessage encrypted = null;
        try {
            MessageId.ErrorId errorId = id.createErrorId(0);
            Header header = new Header(id, errorId, nicks.length);
            encrypted = nicks(receiver, header, nicks);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        } catch (EncryptionException e) {
            System.out.println("Unable to encrypt Message.");
            encrypted =  Server_error.unableToEncrypt(receiver);
        }

        return encrypted;
    }

    /**
     * Creates encrypted message with header passed as an parameter.
     * Message contains packages with one string in each package.
     * @param receiver - the ActiveUser that request message with separated strings
     * @param header - special header, which inform what kind of list is inside message
     * @param strings - strings to send
     * @return - encryptedMessage
     * @throws EncryptionException
     */
    private static EncryptedMessage nicks(ActiveUser receiver, Header header, String[] strings) throws EncryptionException {
        EncryptedMessage encrypted = null;

        if (strings.length == 0){
            return new EncryptedMessage(header);
        } else {
            List<String> nickList = Arrays.asList(strings);
            Message message = new Message(header, nickList);
            return  Encryption.encryptMessage(receiver, message);
        }
    }
}
