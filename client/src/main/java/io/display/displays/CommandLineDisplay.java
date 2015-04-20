/*
 * Copyright (C) 2015 robert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.display.displays;

import io.display.IDisplay;
import io.display.IFormatter;

/**
 *
 * @author robert
 */
public abstract class CommandLineDisplay implements IDisplay
{
    // TO REPLACE BY CONFIGURATION CLASS
    private static final int COMMANDLINE_HEIGHT = 20;
    private static final int COMMANDLINE_WIDTH = 122;
    //
    
    private static final int COMMAND_MESSAGE_HEIGHT = 5;
    
    private String msg = "";
    private String command = "";
    private IFormatter formatter = null;
    
    public abstract String showBody();
    
    private int countHeight(String body)
    {
        return countNumberOfOccurence(body, '\n') + 1;
    }
    
    private int countNumberOfOccurence(String str, char delim)
    {
        int counter = 0;
        
        for (char ch : str.toCharArray())
            if (ch == delim)
                ++counter;
        
        return counter;
    }
    
    private String indent(int i)
    {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < i; ++j)
            result.append(' ');
        
        return result.toString();
    }
    
    private String newLines(int i)
    {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < i; ++j)
            result.append('\n');
        
        return result.toString();
    }
    
    private String trimOrFill(String body)
    {
        int bodyHeight = countHeight(body);
        int wholeHeight = bodyHeight + COMMAND_MESSAGE_HEIGHT;
        
        int diff = wholeHeight - COMMANDLINE_HEIGHT;
        
        if (diff > 0)
        {
            int count = 0;
            int i;
            for (i = 0; i < body.length(); ++i)
            {
                char ch = body.charAt(i);
                if (ch == '\n')
                {
                    ++count;

                    if (count >= diff)
                        break;
                }
            }

            body = body.substring(i + 1);
        }
        else if (diff < 0)
        {
            diff = Math.abs(diff);
            body += newLines(diff);
        }
        
        return body;
    }
    
    @Override
    public void setMsg(String mmsg, boolean error)
    {
        msg = error ? formatter.fg(IFormatter.Color.RED, mmsg) : 
                formatter.fg(IFormatter.Color.GREEN, mmsg);
    }

    @Override
    public void setMsg(String mmsg)
    {
        msg = mmsg;
    }

    @Override
    public String getMsg()
    {
        return msg;
    }

    @Override
    public void setCommand(String newCommand)
    {
        command = newCommand;
    }

    @Override
    public String show()
    {
        String body = showBody();
        body = trimOrFill(body);
        
        String whole = body + '\n';
        whole += formatter.spec(IFormatter.SpecialFormat.UNDERSCORE,
                indent(COMMANDLINE_WIDTH));
        whole += '\n' + msg + '\n';
        whole += formatter.spec(IFormatter.SpecialFormat.UNDERSCORE,
                indent(COMMANDLINE_WIDTH));
        whole += command + '\n';
        
        return whole;
    }

    @Override
    public void setFormatter(IFormatter newFormatter)
    {
        formatter = newFormatter;
    }

    @Override
    public String getCommand()
    {
        return command;
    }
    
}
