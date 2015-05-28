/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Info;
import io.display.displays.ConversationDisplay;
import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public class MessageIncomePlugin extends Plugin
{
    private final Conversation conv;
    
    public MessageIncomePlugin(Conversation cconv)
    {
        conv = cconv;
    }
    
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        // add message to conversation
        Info info = Info.getInstance();
        conv.addMessage(info.getInterlocutorName(), parameters[0]);
        // show conversation display
        pluginManager.setDisplay(id, new ConversationDisplay(conv));
    }
}
