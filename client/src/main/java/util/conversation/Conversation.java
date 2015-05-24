/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.conversation;

import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalTime;

/**
 *
 * @author robert
 */
public class Conversation
{
    LinkedList<Message> messages = new LinkedList<>();
    
    public void start()
    {
        // TODO różne czynności (np. odczytanie ostatnich rozmów)
    }
    
    public void end()
    {
        // TODO różne czynności (np. zapisanie ostatnich rozmów)
        // na końcu czyścimy listę wiadomości
        messages.clear();
    }

    public void addMessage(String username, String message)
    {
        // jeżeli pierwsza wiadomość lub poprzednia była od innego użytkownika
        // to tworzymy nową
        if (messages.size() == 0 || 
                !messages.getLast().getUsername().equals(username))
        {
            Message m = new Message(message, username, LocalTime.now());
            messages.add(m);
        }
        // inaczej dołączamy do istniejącej ostatniej już
        else
        {
            messages.getLast().append(message, LocalTime.now());
        }
    }
    
    public List<Message> getMessages()
    {
        return messages;
    }
}
