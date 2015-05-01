package responders.registration;

import containers.Registered;
import message.types.*;
import message.utils.Encryption;
import message.utils.MessageCreator;
import message.utils.MessageSender;
import message.utils.Messages;
import messages.IncorrectMessageId;
import messages.MessageId;
import responders.AbstractMessageHandler;
import rsa.exceptions.EncryptionException;
import user.ActiveUser;
import user.UserData;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * This class handles the messages with MessageId = 2 (SIGN_UP)
 * This class lets the user to register.
 *
 * Created by tochur on 19.04.15.
 */
public class SignUpMessageHandler extends AbstractMessageHandler {

    public void handle(ActiveUser activeUser, EncryptedMessage encryptedMessage){
        Message message = null;

        try {
            message = Encryption.decryptMessage(encryptedMessage, activeUser);
        } catch (EncryptionException e) {
            e.printStackTrace();
        }

        List<String> strings = message.getPackages();
        String login = strings.get(0);
        String password = strings.get(1);
        System.out.print("Login: " + login);
        System.out.print("Password: " + password);
        if(!Registered.getInstance().isLoginFree(login)){
            System.out.print("Login is occupied");
            Message message1 = Messages.occupiedLogin();
            try {
                MessageSender.sendMessage(activeUser, Encryption.encryptMessage(activeUser, message1));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncryptionException e) {
                e.printStackTrace();
            }
        }else{
            Registered.getInstance().addNewClient(new UserData(strings.get(0), strings.get(1)));
            System.out.print("New user registered: " + strings.get(0));
            EncryptedMessage message1 = Messages.loggedSuccessfully();
            try {
                MessageSender.sendMessage(activeUser, message1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
