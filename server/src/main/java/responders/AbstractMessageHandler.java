package responders;

import message.EncryptedMessage;
import message.IMessage;
import messages.IncorrectMessageId;
import responders.exceptions.WrongUserStateException;
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
 * Created by tochur on 24.04.15.
 */
public abstract class AbstractMessageHandler implements MessageHandler {
    public abstract void handle(ActiveUser activeUser, EncryptedMessage message);
    protected abstract boolean isMessageAppropriate(ActiveUser activeUser, IMessage message);
}
