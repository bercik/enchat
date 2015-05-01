package responders.implementations;

import containers.Registered;
import message.generarators.Sign_Up;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.ActiveUser;
import user.UserState;
import utils.Validator;

import java.io.IOException;
import java.util.List;

/**
 * This class handles the messages with MessageId = 2 (SIGN_UP)
 * This class lets the user to register.
 *
 * Created by tochur on 19.04.15.
 */
public class SignUpMessageHandler extends AbstractMessageHandler {

    //Ancillary variables
    String login;
    String password;
    List<String> strings;

    /**
     * Constructor of handler
     *
     * @param activeUser - author of the message
     * @param encrypted  - received message
     */
    public SignUpMessageHandler(ActiveUser activeUser, EncryptedMessage encrypted) {
        super(activeUser, encrypted);
        permittedStates = new UserState[] {UserState.AFTER_KEY_EXCHANGE};
    }

    @Override
    protected void processMessage() throws DecryptingException {
        decryptMessage();
    }

    @Override
    protected void createAncillaryVariables() {
        this.strings = this.message.getPackages();
        this.login = strings.get(0);
        this.password = strings.get(1);
    }

    @Override
    protected void reaction() throws ReactionException {
        EncryptedMessage answer;

        if ( !Validator.isLoginCorrect(login)){
            answer = Sign_Up.incorrectLogin();
        }else if( !Validator.isPasswordLengthCorrect(password)){
            answer = Sign_Up.badPasswordLength();
        }else if ( !Registered.getInstance().isLoginFree(login)) {
            answer = Sign_Up.busyLogin();
        }else{
            answer = Sign_Up.ok();
        }

        try{
            MessageSender.sendMessage(activeUser, answer);
        } catch (IOException e) {
            throw new ReactionException();
        }

    }
}
