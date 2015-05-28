/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Info;
import io.display.displays.ConversationDisplay;
import messages.MessageId;
import plugin.IState;
import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public class MessageOutcomePlugin extends Plugin
{
    private IState currentState;
    private String message;
    private final Conversation conv;

    public MessageOutcomePlugin(Conversation cconv)
    {
        conv = cconv;
        currentState = new State1();
        message = null;
    }

    @Override
    public void reset()
    {
        currentState = new State1();
        message = null;
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
            // zapisujemy wiadomość w referencji klasy
            message = parameters[0];
            // wysyłamy wiadomość
            pluginManager.send(id, new String[]
            {
                message
            });

            // zwracamy kolejny stan
            return new State2();
        }
    }

    private class State2 implements IState
    {
        @Override
        public IState run(int error, String[] parameters)
        {
            MessageId messageId = MessageId.createMessageId(id);
            MessageId.ErrorId errorId = messageId.createErrorId(error);
            pluginManager.updateControllerError(error);

            switch (errorId)
            {
                case OK:
                    // add message to conversation
                    Info info = Info.getInstance();
                    conv.addMessage(info.getUserName(), message);
                    // show conversation display
                    pluginManager.setDisplay(id, new ConversationDisplay(conv));
                    break;
                case FAILED:
                    String msg = "Nie udało się dostarczyć wiadomości";
                    pluginManager.setMsg(msg, true);
                    break;
                default:
                    msg = "Nieobsłużony error " + errorId.toString() + " w "
                            + messageId.toString();
                    throw new RuntimeException(msg);
            }

            return new State1();
        }
    }
}
