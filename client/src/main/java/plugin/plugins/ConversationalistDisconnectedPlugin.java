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
public class ConversationalistDisconnectedPlugin extends EndConversationPlugin
{
    public ConversationalistDisconnectedPlugin(Conversation conv)
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
        // wywołujemy metodę, która zakańcza rozmowę
        endConversation(error);
    }
}
