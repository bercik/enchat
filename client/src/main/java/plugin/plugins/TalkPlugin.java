/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Configuration;
import app_info.Info;
import messages.MessageId;
import plugin.IState;
import util.Authentication;
import util.StringFormatter;
import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public class TalkPlugin extends StartConversationPlugin
{
    private IState currentState = new State1();
    private String username;

    public TalkPlugin(Conversation conv)
    {
        super(conv);
    }

    @Override
    public void reset()
    {
        currentState = new State1();
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
            // sprawdzamy czy podano username
            if (parameters.length > 0)
            {
                username = parameters[0];
                // sprawdzamy czy wpisany login jest poprawny
                if (!Authentication.isLoginCorrect(username))
                {
                    // wyświetlamy informację o źle wpisanym loginie
                    String msg = "Login " + username + " jest nieprawidłowy";
                    pluginManager.setMsg(msg, true);
                    pluginManager.updateControllerError(error);
                    return new State1();
                }

                // informacja (potrzebna do pobrania naszej nazwy użytkownika
                Info info = Info.getInstance();
                // sprawdzamy czy nie próbujemy rozmawiać z samym sobą
                if (username.equals(info.getUserName()))
                {
                    String msg = "Nie możesz nawiązać konwersacji z samym sobą";
                    pluginManager.setMsg(msg, true);
                    pluginManager.updateControllerError(error);
                    return new State1();
                }

                // wyświetlamy informację użytkownikowi
                String msg = "Próbuję nawiązać konwersację z " + username;
                pluginManager.setMsg(msg, false);
                // wysyłamy prośbę o konwersację z użytkownikiem
                pluginManager.send(id, new String[]
                {
                    username
                });

                // zwracamy kolejny stan
                return new State2();
            }
            else
            {
                // wyświetlamy informacje o błędzie
                String msg = StringFormatter.badCommand("talk");
                pluginManager.setMsg(msg, true);

                // zwracamy pierwszy stan
                return new State1();
            }
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

            String msg;

            switch (errorId)
            {
                case OK:
                    // wywołujemy metodę klasy bazowej odpowiedzialną za
                    // zainicjalizowanie konwersacji
                    startConversation(parameters);
                    break;
                case USER_NOT_LOGGED:
                    msg = "Użytkownik " + username + " jest niezalogowany";
                    pluginManager.setMsg(msg, true);
                    break;
                case BUSY_USER:
                    msg = "Użytkownik " + username + " jest zajęty";
                    pluginManager.setMsg(msg, true);
                    break;
                case CONVERSATION_WITH_ANOTHER_USER:
                    msg = "Rozmawiasz już z innym użytkownikiem";
                    pluginManager.setMsg(msg, true);
                    break;
                case ON_BLACK_LIST:
                    Configuration conf = Configuration.getInstance();
                    msg = "Użytkownik " + username + " znajduje się na "
                            + "Twojej czarnej liście. Odblokuj go (komenda "
                            + conf.getCommandPrefix() + "unblock)" + ", aby móc "
                            + "z nim rozmawiać";
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
