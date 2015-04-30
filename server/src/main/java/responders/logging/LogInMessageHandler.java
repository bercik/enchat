package responders.logging;

import containers.Registered;
import message.types.EncryptedMessage;
import message.utils.Encryption;
import message.types.Message;
import message.utils.MessageCreator;
import message.utils.MessageSender;
import messages.IncorrectMessageId;
import messages.MessageId;
import responders.MessageHandler;
import user.ActiveUser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * Handles the messages with id = 1 (LOG_IN)
 *
 * Created by tochur on 19.04.15.
 */
public class LogInMessageHandler implements MessageHandler {
    public void handle(ActiveUser activeUser, EncryptedMessage encryptedMessage) {
        Message message;
        try {
            message = Encryption.decryptMessage(encryptedMessage, activeUser);
            String nick = message.getPackages().get(0);
            String password = message.getPackages().get(1);

            boolean exist = Registered.getInstance().doesUserExist(nick, password);
            Message answer;
            if(exist){
                answer = MessageCreator.createInfoMessage(MessageId.LOG_IN, 0, "Logged as: " + nick);
            }else{
                answer = MessageCreator.createInfoMessage(MessageId.LOG_IN, 1, "Login or password incorrect.");
            }
            try {
                MessageSender.sendMessage(activeUser, Encryption.encryptMessage(activeUser, answer));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IncorrectMessageId incorrectMessageId) {
                incorrectMessageId.printStackTrace();
            }

        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }
}
