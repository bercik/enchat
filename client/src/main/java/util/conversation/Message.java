/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.conversation;

import org.joda.time.*;

/**
 *
 * @author robert
 */
public class Message
{
    private String message;
    private String username;
    private LocalTime dateFrom;
    private LocalTime dateTo;

    public Message(String message, String username, LocalTime dateFrom)
    {
        this.message = message;
        this.username = username;
        this.dateFrom = dateFrom;
        this.dateTo = dateFrom;
    }
    
    public void append(String newMessage, LocalTime date)
    {
        // dodajemy wiadomość
        message += "\n" + newMessage;
        
        // ustawiamy czas najnowszej wiadomości
        dateTo = date;
    }
    
    public String getMessage()
    {
        return message;
    }

    public String getUsername()
    {
        return username;
    }

    public LocalTime getDateFrom()
    {
        return dateFrom;
    }

    public LocalTime getDateTo()
    {
        return dateTo;
    }
}
