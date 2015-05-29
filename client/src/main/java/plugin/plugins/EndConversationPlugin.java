/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Info;
import app_info.State;
import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public abstract class EndConversationPlugin extends Plugin
{
    private final Conversation conv;
    
    public EndConversationPlugin(Conversation cconv)
    {
        conv = cconv;
    }

    protected void endConversation(int error)
    {
        // info
        Info info = Info.getInstance();
        // zakańczamy konwersację
        conv.end();
        //zmieniamy stan aplikacji
        pluginManager.setAppState(State.LOGGED);
        // wyświetlamy wiadomość użytkownikowi
        String msg = "Zakończono rozmowę z " + info.getInterlocutorName();
        pluginManager.setMsg(msg, false);
        // zmieniamy nazwę rozmówcy na null
        info.setInterlocutorName(null);
        // wysyłamy error do kontrolera
        pluginManager.updateControllerError(error);
    }
}
