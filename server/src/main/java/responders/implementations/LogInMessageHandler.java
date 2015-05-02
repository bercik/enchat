package responders.implementations;

import containers.Logged;
import containers.Registered;
import message.generarators.Log_In;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.User;
import user.UserData;
import user.UserState;

import java.io.IOException;

/**
 * Handles the messages with id = 1 (LOG_IN)
 *
 * Created by tochur on 19.04.15.
 */
public class LogInMessageHandler extends AbstractMessageHandler {
    EncryptedMessage answer = null;
    //Ancillary
    String nick;
    String password;

    /**
     * Constructor of handler
     *
     * @param user - author of the message
     * @param encrypted  - received message
     */
    public LogInMessageHandler(User user, EncryptedMessage encrypted) {
        super(user, encrypted);
    }

    @Override
    protected void processMessage() throws DecryptingException {
        decryptMessage();
    }

    @Override
    protected void createAncillaryVariables(){
        this.nick = message.getPackages().get(0);
        this.password = message.getPackages().get(1);
    }

    @Override
    protected void reaction() throws ReactionException {
        /*Scan for errors*/
        boolean exist = Registered.getInstance().doesUserExist(nick, password);
        boolean notOverload = Logged.getInstance().canLogNextUser();

        /*Creating answer*/
        if (! exist) {
            answer = Log_In.badLoginOrPassword();
        } else if (!notOverload) {
            answer = Log_In.toMuchUserLogged();
        } else {
            answer = Log_In.loggedSuccessfully();
            sender.setData(new UserData(nick, password));
            sender.setState(UserState.LOGGED);
        }

        /*Sending answer*/
        try {
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
