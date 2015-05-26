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
    private Conversation conv;
    
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
        
        // wyświetl nagłówek
        String header = "Rozmowa z " + info.getInterlocutorName() + "\n";
        String body = centerString(formatter.bg(INTERLOCUTOR_COLOR, header));
        // linia
        body += formatter.spec(IFormatter.SpecialFormat.UNDERSCORE, 
                indent(conf.getWidth())) + "\n";
        
        // wiadomości
        for (Message m : conv.getMessages())
        {
            body += showMessage(m) + "\n";
        }
        
        // usuń ostatni znak nowej lini
        body = body.substring(0, body.length() - 1);
        
        return body;
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
