/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.conversation;

import app_info.Info;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalTime;

/**
 *
 * @author robert
 */
public class Conversation
{
    // rozszerzenie plików z zapisanymi rozmowami
    private static final String FILE_EXT = ".cv";
    // maksymalna ilość wiadomości w liście
    private static final int MAX_MESSAGES = 100;
    // wiadomości
    LinkedList<Message> messages = new LinkedList<>();
    
    public void start(String passwordHash)
    {
        // info
        Info info = Info.getInstance();
        // tworzymy nazwę pliku (nasz_login-login_rozmówcy.cv)
        String fileName = info.getUserName() + "-" + 
                info.getInterlocutorName() + FILE_EXT;
        // próbujemy odczytać rozmowę
        ConversationFileReader reader = new ConversationFileReader();
        try
        {
            List<Message> returned = reader.read(fileName, passwordHash);
            messages.addAll(returned);
        }
        catch (ConversationFileReadException ex)
        {
            // do nothing
        }
    }
    
    public void end(String passwordHash)
    {
        // info
        Info info = Info.getInstance();
        // tworzymy nazwę pliku (nasz_login-login_rozmówcy.cv)
        String fileName = info.getUserName() + "-" + 
                info.getInterlocutorName() + FILE_EXT;
        // zapisujemy rozmowę
        ConversationFileSaver saver = new ConversationFileSaver();
        try
        {
            saver.save(messages, fileName, passwordHash);
        }
        catch (ConversationFileSaveException ex)
        {
            // do nothing TODELETE
            throw new RuntimeException(ex);
        }
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
            
            if (messages.size() > MAX_MESSAGES)
            {
                messages.removeFirst();
            }
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
