package responders.implementations;

import containers.Logged;
import containers.Registered;
import message.generarators.Log_In;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.ActiveUser;
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
     * @param activeUser - author of the message
     * @param encrypted  - received message
     */
    public LogInMessageHandler(ActiveUser activeUser, EncryptedMessage encrypted) {
        super(activeUser, encrypted);
        permittedStates = new UserState[] {UserState.AFTER_KEY_EXCHANGE};
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
        if (exist && notOverload) {
            answer = Log_In.loggedSuccessfully();
        } else if (!exist) {
            answer = Log_In.badLoginOrPassword();
        } else if (!notOverload) {
            answer = Log_In.toMuchUserLogged();
        }

        /*Sending answer*/
        try {
            MessageSender.sendMessage(activeUser, answer);
        } catch (IOException e) {
            throw new ReactionException();
        }
    }
}
