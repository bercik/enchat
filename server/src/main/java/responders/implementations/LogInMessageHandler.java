package responders.implementations;

import containers.Logged;
import containers.Registered;
import containers.exceptions.AlreadyInCollection;
import containers.exceptions.OverloadedCannotAddNew;
import message3.generators.Messages;
import message3.types.EncryptedMessage;
import message3.utils.MessageSender;
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
    EncryptedMessage answer;
    //Ancillary
    String nick;
    String password;

    /**
     * Constructor of handler
     *
     * @param user - author of the message
     * @param encrypted  - received message
     */
    public LogInMessageHandler(User user, EncryptedMessage encrypted, Messages messages) {
        super(user, encrypted, messages);
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

        //Authorization
        boolean exist = Registered.getInstance().doesUserExist(nick, password);
        if ( !exist) {
            answer = messages.logIn().badLoginOrPassword();
        }

        //Add User to logged
        UserData userData = new UserData(nick, password);
        Logged logged = Logged.getInstance();
        try {
            logged.addUser(sender);
            changeUserStateToLogged(sender, userData);
            answer = messages.logIn().loggedSuccessfully();
        } catch (OverloadedCannotAddNew overloadedCannotAddNew) {
            answer = messages.logIn().toMuchUserLogged();
        } catch (AlreadyInCollection alreadyInCollection) {
            answer = messages.logIn().alreadyLogged();
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


    private void changeUserStateToLogged(User user, UserData userData){
        user.setData(userData);
        user.setState(UserState.LOGGED);
    }
}
