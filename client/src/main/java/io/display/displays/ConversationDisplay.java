/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import app_info.Configuration;
import app_info.Info;
import io.display.IFormatter;
import org.joda.time.LocalTime;
import util.conversation.Conversation;
import util.conversation.Message;

/**
 *
 * @author robert
 */
public class ConversationDisplay extends CommandLineDisplay
{
    // wysokość nagłówka (rozmowa z ..., ciągła linia)
    private static final int HEADER_HEIGHT = 2;
    
    private final Conversation conv;
    
    private static final IFormatter.Color OUR_COLOR = IFormatter.Color.CYAN;
    private static final IFormatter.Color INTERLOCUTOR_COLOR = 
            IFormatter.Color.MAGENTA;
    
    public ConversationDisplay(Conversation cconv)
    {
        conv = cconv;
    }
    
    @Override
    protected String showBody()
    {
        // info i config
        Info info = Info.getInstance();
        Configuration conf = Configuration.getInstance();
        
        // stwórz nagłówek
        String header = "Rozmowa z " + info.getInterlocutorName();
        header = centerString(formatter.bg(INTERLOCUTOR_COLOR, header)) + "\n";
        // linia
        header += formatter.spec(IFormatter.SpecialFormat.UNDERSCORE, 
                indent(conf.getWidth())) + "\n";
        
        // ciało
        String body = "";
        // wiadomości
        for (Message m : conv.getMessages())
        {
            body += showMessage(m) + "\n";
        }
        
        // usuń ostatni znak nowej lini jeżeli dodano jakieś wiadomości
        if (conv.getMessages().size() > 0)
        {
            body = body.substring(0, body.length() - 1);
        }
        // utnij jeżeli wychodzi poza dostępny obszar
        body = trim(body);
        
        // zwróc połączenie nagłówka i ciała
        return header + body;
    }
    
    private String trim(String body)
    {
        Configuration conf = Configuration.getInstance();
        
        int bodyHeight = countHeight(body);
        int availableHeight = conf.getHeight() - 
                (HEADER_HEIGHT + COMMAND_MESSAGE_HEIGHT);
        
        int diff = availableHeight - bodyHeight;
        
        if (diff < 0)
        {
            int toTrim = Math.abs(diff);
            
            return body.substring(searchNthOccurence(body, '\n', toTrim) + 1);
        }
        else
        {
            return body;
        }
    }
    
    private int searchNthOccurence(String str, Character search, int n)
    {
        for (int i = 0; i < str.length(); ++i)
        {
            char ch = str.charAt(i);
            
            if (ch == search)
            {
                if (--n == 0)
                    return i;
            }
        }
        
        return -1;
    }
    
    private String showMessage(Message message)
    {
        // info
        Info info = Info.getInstance();
        // wyciągamy zmienne do lokalnych referencji
        String username = message.getUsername();
        LocalTime dateFrom = message.getDateFrom();
        LocalTime dateTo = message.getDateTo();
        String msg = message.getMessage();
        
        // tworzymy nagłówek
        String header = username + " (" + getTime(dateFrom);
        if (!equalsTime(dateFrom, dateTo))
        {
            header += "-" + getTime(dateTo);
        }
        header += "):";
        
        String result = "";
        if (username.equals(info.getUserName()))
        {
            result += formatter.fg(OUR_COLOR, header) + "\n";
        }
        else
        {
            result += formatter.fg(INTERLOCUTOR_COLOR, header) + "\n";
        }
        
        // dodajemy treść wiadomości robiąc wcięcie
        String[] split = msg.split("\n");
        for (String s : split)
        {
            result += indent(2) + s + "\n";
        }
        
        // remove last new line character
        result = result.substring(0, result.length() - 1);
        
        return result;
    }
    
    private String getTime(LocalTime time)
    {
        return Integer.toString(time.getHourOfDay()) + ":" +
                Integer.toString(time.getMinuteOfHour());
    }
    
    private boolean equalsTime(LocalTime time1, LocalTime time2)
    {
        return (time1.getHourOfDay() == time2.getHourOfDay()) && 
                (time1.getMinuteOfHour() == time2.getMinuteOfHour());
    }
}
