/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Configuration;
import app_info.Info;
import app_info.State;
import messages.MessageId;

/**
 *
 * @author robert
 */
public class RegisterPlugin extends AuthenticationPlugin
{
    @Override
    protected void deliverError(int error)
    {
        MessageId messageId = MessageId.createMessageId(id);
        MessageId.ErrorId errorId = messageId.createErrorId(error);
        pluginManager.updateControllerError(error);

        String msg;

        switch (errorId)
        {
            case OK:
                Configuration conf = Configuration.getInstance();
                msg = "Zarejestrowałeś się jako: " + login + ". "
                        + "Aby się zalogować wpisz " + conf.getCommandPrefix()
                        + "login";
                pluginManager.setMsg(msg, false);
                break;
            case BUSY_LOGIN:
                msg = "Login " + login + " jest zajęty";
                pluginManager.setMsg(msg, true);
                break;
            case INCORRECT_LOGIN:
                msg = "Login " + login + " jest niepoprawny";
                pluginManager.setMsg(msg, true);
                break;
            case BAD_PASSWORD_LENGTH:
                msg = "Hasło złej długości";
                pluginManager.setMsg(msg, true);
                break;
            case TO_MUCH_REGISTERED:
                msg = "Za dużo osób zarejestrowanych na serwerze. Nie mam gdzie"
                        + " Cię wcisnąć, spróbuj ponownie później";
                pluginManager.setMsg(msg, true);
                break;
            default:
                msg = "Nieobsłużony error " + errorId.toString() + " w "
                        + messageId.toString();
                throw new RuntimeException(msg);
        }
    }
}
