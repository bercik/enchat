package responders;

import message.EncryptedMessage;
import messages.IncorrectMessageId;
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
 * Created by tochur on 19.04.15.
 */
public interface MessageHandler {
    public void handle(ActiveUser activeUser, EncryptedMessage message);
}
