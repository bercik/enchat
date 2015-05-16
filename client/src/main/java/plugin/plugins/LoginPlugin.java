/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Info;
import app_info.State;
import messages.MessageId;

/**
 *
 * @author robert
 */
public class LoginPlugin extends AuthenticationPlugin
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
                Info info = Info.getInstance();
                info.setUserName(login);
                pluginManager.setAppState(State.LOGGED);
                msg = "Jesteś zalogowany jako: " + login;
                pluginManager.setMsg(msg, false);
                break;
            case BAD_LOGIN_OR_PASSWORD:
                msg = "Zły login lub hasło";
                pluginManager.setMsg(msg, true);
                break;
            case TOO_MUCH_USERS_LOGGED:
                msg = "Za dużo użytkowników zalogowanych na serwerze. "
                        + "Spróbuj ponownie później";
                pluginManager.setMsg(msg, true);
                break;
            case ALREADY_LOGGED:
                msg = "Jesteś już zalogowany";
                pluginManager.setMsg(msg, true);
                break;
        }
    }
}
