/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public class EndTalkPlugin extends EndConversationPlugin
{
    public EndTalkPlugin(Conversation conv)
    {
        super(conv);
    }
    
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        // wysyłamy wiadomość do serwera
        pluginManager.send(id, new String[0]);
        // uruchamiamy metodę, która zakańcza konwersację
        endConversation(error);
    }
}
