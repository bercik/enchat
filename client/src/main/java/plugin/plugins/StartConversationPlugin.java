/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Info;
import app_info.State;
import rsa.PublicKeyInfo;
import rsa.RSA;
import util.conversation.Conversation;

/**
 *
 * @author robert
 */
public abstract class StartConversationPlugin extends Plugin
{
    private final Conversation conv;

    public StartConversationPlugin(Conversation cconv)
    {
        conv = cconv;
    }

    protected void startConversation(String[] params)
    {
        // wyciągamy parametry do lokalnych referencji
        String username = params[0];
        // modulus i exponent zostały podzielone na dwie części każdy, ponieważ
        // nie zmieściłiby się w limicie zaszyfrowanej wiadomości wynikającym
        // z algorytmu SHA-256 (RSA).
        String modulusString = params[1] + params[2];
        String exponentString = params[3] + params[4];
        // klucz publiczny rozmówcy
        PublicKeyInfo interlocutorPublicKeyInfo;
        try
        {
            // zamieniamy stringi na tablicę bajtów
            byte[] modulusBytes = modulusString.getBytes(RSA.STRING_CODING);
            byte[] exponentBytes = exponentString.getBytes(RSA.STRING_CODING);

            // tworzymy publiczny klucz osoby z którą będziemy rozmawiać
            interlocutorPublicKeyInfo = 
                    new PublicKeyInfo(modulusBytes, exponentBytes);
        }
        catch (Exception ex)
        {
            // wyświetlamy informację o błędzie użytkownikowi
            String msg = "Nie udało się nawiązać konwersacji z użytkownikiem "
                    + username + " z powodu błędu w odczycie klucza publicznego";
            pluginManager.setMsg(msg, true);
            return;
        }
        // ustawiamy klucz rozmówcy
        pluginManager.setInterlocutorPublicKeyInfo(
                interlocutorPublicKeyInfo);
        // inicjalizujemy konwersację
        conv.start();
        // ustawiamy nazwę rozmówcy globalnie
        Info info = Info.getInstance();
        info.setInterlocutorName(username);
        // zmieniamy stan aplikacji
        pluginManager.setAppState(State.CONVERSATION);
        // wyświetlamy informację użytkownikowi
        String msg = "Udało się nawiązać konwersację z użytkownikiem "
                + username;
        pluginManager.setMsg(msg, false);
    }
}
