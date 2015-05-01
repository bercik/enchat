package responders.logging;

import containers.Logged;
import containers.Registered;
import message.types.EncryptedMessage;
import message.utils.Encryption;
import message.types.Message;
import message.utils.MessageCreator;
import message.utils.MessageSender;
import message.utils.Messages;
import messages.IncorrectMessageId;
import messages.MessageId;
import responders.MessageHandler;
import rsa.exceptions.EncryptingException;
import rsa.exceptions.EncryptionException;
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

        try{
            /*Decrypting and reading message*/
            message = Encryption.decryptMessage(encryptedMessage, activeUser);

            String nick = message.getPackages().get(0);
            String password = message.getPackages().get(1);

            /*Scan for errors*/
            boolean exist = Registered.getInstance().doesUserExist(nick, password);
            boolean notOverload = Logged.getInstance().canLogNextUser();

            /*Creating answer*/
            Message answer = null;
            if (exist && notOverload) {
                answer = Messages.loginConfirmation("Logged as: " + nick);
            } else if (!exist) {
                answer = Messages.wrongNickOrPassword();
            } else if (!notOverload) {
                answer = Messages.toMuchUsersLogged();
            }

            /*Sending answer*/
            MessageSender.sendMessage(activeUser, Encryption.encryptMessage(activeUser, answer));

        } catch (EncryptionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
