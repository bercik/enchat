/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import messages.MessageId;

/**
 *
 * @author robert
 */
public class UnblockPlugin extends BlockUnblockPlugin
{
    public UnblockPlugin()
    {
        super("unblock");
    }
    
    @Override
    protected void deliverError(int error)
    {
        MessageId messageId = MessageId.createMessageId(id);
        MessageId.ErrorId errorId = messageId.createErrorId(error);
        pluginManager.updateControllerError(error);
        
        String msg;
        
        switch(errorId)
        {
            case OK:
                msg = "Użytkownik " + getUsername() + " został usunięty z "
                        + "czarnej listy";
                pluginManager.setMsg(msg, false);
                break;
            case USER_NOT_ON_BLACKLIST:
                msg = "Użytkownik " + getUsername() + " nie znajduje się na "
                        + "czarnej liście";
                pluginManager.setMsg(msg, true);
                break;
            case USER_NOT_EXIST:
                msg = "Użytkownik " + getUsername() + " nie istnieje";
                pluginManager.setMsg(msg, true);
                break;
        }
    }
}
