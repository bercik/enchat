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
public class BlockPlugin extends BlockUnblockPlugin
{
    public BlockPlugin()
    {
        super("block");
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
                msg = "Użytkownik " + getUsername() + " dodany na czarną listę";
                pluginManager.setMsg(msg, false);
                break;
            case USER_DOESNT_EXIST:
                msg = "Użytkownik " + getUsername() + " nie istnieje";
                pluginManager.setMsg(msg, true);
                break;
            case TOO_MUCH_USERS_ON_BLACKLIST:
                msg = "Masz za dużo osób na czarnej liście. Usuń kogoś i "
                        + "spróbuj ponownie";
                pluginManager.setMsg(msg, true);
                break;
            case ALREADY_ADDED:
                msg = "Użytkownik " + getUsername() + " już znajduje się na "
                        + "czarnej liście";
                pluginManager.setMsg(msg, true);
                break;
        }
    }
}
