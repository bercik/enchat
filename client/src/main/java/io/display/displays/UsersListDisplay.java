/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import app_info.Configuration;
import io.display.IFormatter;

/**
 *
 * @author robert
 */
public class UsersListDisplay extends CommandLineDisplay
{
    private static final IFormatter.Color FIRST_COLOR = IFormatter.Color.WHITE;
    private static final IFormatter.Color SECOND_COLOR = IFormatter.Color.YELLOW;
    private final String header;
    private final String[] users;

    public UsersListDisplay(String header, String[] users)
    {
        this.header = header;
        this.users = users;
    }

    @Override
    protected String showBody()
    {
        String underscoreHeader = 
                formatter.spec(IFormatter.SpecialFormat.UNDERSCORE, header);
        String body = centerString(underscoreHeader) + "\n\n";

        // zmienne pomocnicze
        Configuration conf = Configuration.getInstance();
        int width = conf.getWidth();
        boolean first = true;

        for (int i = 0; i < users.length; ++i)
        {
            String user = users[i];
            
            if (width - user.length() <= 1)
            {
                body += "\n";
                width = conf.getWidth();
            }
            
            String toAppend = user;
            // nie dodawaj przecinka na końcu
            if (i != users.length - 1)
                toAppend += ", ";
            
            if (first)
                body += formatter.fg(FIRST_COLOR, toAppend);
            else
                body += formatter.fg(SECOND_COLOR, toAppend);
            
            width -= user.length() + 2;
            first = !first;
        }

        return body;
    }
}
