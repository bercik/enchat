/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import messages.MessageId;
import plugin.IState;
import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public class ConversationIncomePlugin extends StartConversationPlugin
{
    private IState currentState = new State1();

    public ConversationIncomePlugin(Conversation conv)
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
        currentState = currentState.run(error, parameters);
    }

    private class State1 implements IState
    {
        @Override
        public IState run(int error, String[] parameters)
        {
            MessageId messageId = MessageId.createMessageId(id);
            MessageId.ErrorId errorId = messageId.createErrorId(error);

            String msg;

            switch (errorId)
            {
                case OK:
                    // wywołujemy metodę klasy bazowej odpowiedzialną za
                    // zainicjalizowanie konwersacji
                    StartConversation(parameters);
                    break;
                case IM_BUSY:
                    msg = "Użytkownik " + parameters[0] + " próbował się z "
                            + "Tobą połączyć";
                    pluginManager.setMsg(msg, false);
                    break;
                default:
                    msg = "Nieobsłużony error " + errorId.toString()
                            + " w " + messageId.toString();
                    throw new RuntimeException(msg);
            }
            
            return new State1();
        }
    }
}
