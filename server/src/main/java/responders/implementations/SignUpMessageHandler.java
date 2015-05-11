package responders.implementations;

import containers.Registered;
import containers.exceptions.AlreadyInCollection;
import containers.exceptions.OverloadedCannotAddNew;
import message.generators.Messages;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.User;
import user.UserData;
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
     * @param user - author of the message
     * @param encrypted  - received message
     */
    public SignUpMessageHandler(User user, EncryptedMessage encrypted, Messages messages) {
        super(user, encrypted, messages);
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
            answer = messages.signUp().incorrectLogin();
        }else if( !Validator.isPasswordLengthCorrect(password)){
            answer = messages.signUp().badPasswordLength();
        }else{
            try {
                Registered.getInstance().addNewClient(new UserData(login, password));
                answer = messages.signUp().ok();
            } catch (AlreadyInCollection alreadyInCollection) {
                answer = messages.signUp().busyLogin();
            } catch (OverloadedCannotAddNew overloadedCannotAddNew) {
                answer = messages.signUp().toManyAccounts();
            }
        }

        try{
            MessageSender.sendMessage(sender, answer);
        } catch (IOException e) {
            throw new ReactionException();
        }

    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] {UserState.AFTER_KEY_EXCHANGE};
    }
}
