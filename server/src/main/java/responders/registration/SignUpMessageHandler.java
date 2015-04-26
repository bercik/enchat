package responders.registration;

import containers.Registered;
import message.*;
import message.utils.MessageSender;
import messages.IncorrectMessageId;
import messages.MessageId;
import responders.AbstractMessageHandler;
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
        //isMessageAppropriate(activeUser, message);
        List<String> strings = message.getPackages();
        String login = strings.get(0);
        String password = strings.get(1);
        System.out.print("Login: " + login);
        System.out.print("Password: " + password);
        if(!Registered.getInstance().isLoginFree(login)){
            System.out.print("Login is occupied");
            Message message1 = MessageCreator.createHeaderMessage(MessageId.SIGN_UP, 1);
            try {
                MessageSender.sendMessage(activeUser, Encryption.encryptMessage(activeUser, message1));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IncorrectMessageId incorrectMessageId) {
                incorrectMessageId.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (SignatureException e) {
                e.printStackTrace();
            }
        }else{
            Registered.getInstance().addNewClient(new UserData(strings.get(0), strings.get(1)));
            System.out.print("You has been registered as a: " + strings.get(0));
            Message message1 = MessageCreator.createHeaderMessage(MessageId.SIGN_UP, 0);
            try {
                MessageSender.sendMessage(activeUser, Encryption.encryptMessage(activeUser, message1));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IncorrectMessageId incorrectMessageId) {
                incorrectMessageId.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (SignatureException e) {
                e.printStackTrace();
            }
        }
        //Message message2 = MessageCreator.createInfoMessage(MessageId.SERVER_MESSAGE,0,"Wiadomość zaszyfrowana");
       /* try {
            MessageSender.sendMessage(activeUser,Encryption.encryptMessage(activeUser,message2));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IncorrectMessageId incorrectMessageId) {
            incorrectMessageId.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected boolean isMessageAppropriate(ActiveUser activeUser, IMessage message) {
        switch ( activeUser.getState() ) {
            case CONNECTED_TO_SERVER:
                return true;
            case CONNECTED_WITH_OTHER:
                Message errorMessage = MessageCreator.createInfoMessage(MessageId.SIGN_UP, 1, "You should log out before.");
                try {
                    EncryptedMessage encryptedMessage = null;
                    try {
                        encryptedMessage = Encryption.encryptMessage(activeUser, errorMessage);
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (SignatureException e) {
                        e.printStackTrace();
                    }
                    try {
                        MessageSender.sendMessage(activeUser, encryptedMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                } catch (IncorrectMessageId incorrectMessageId) {
                    incorrectMessageId.printStackTrace();
                }

        }
        return false;
    }
}
