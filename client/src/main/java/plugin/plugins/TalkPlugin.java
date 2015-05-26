/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import io.display.displays.ConversationDisplay;
import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public class TalkPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        // TODO
        Conversation conv = new Conversation();
        conv.addMessage("Tomek", "siema");
        conv.addMessage("Robert", "siema, co słychać?");
        conv.addMessage("Tomek", "Jest przyjemnie, piję zimny sok.");
        conv.addMessage("Tomek", "A u Ciebie?");
        conv.addMessage("Robert", "Potrzebuję pieniędzy, przelejesz mi?");
        conv.addMessage("Tomek", "Spoko, wyślij mi swój numer konta");
        conv.addMessage("Robert", "10120421402197501750125702510");
        
        pluginManager.setDisplay(id, new ConversationDisplay(conv));
    }
}
