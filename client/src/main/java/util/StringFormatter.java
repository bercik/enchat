/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import app_info.Configuration;
import java.util.List;

/**
 *
 * @author robert
 */
public class StringFormatter
{
    public static String badCommand(String command)
    {
        Configuration conf = Configuration.getInstance();
        String result = "Złe wywołanie komendy "
                + Configuration.getCommandPrefix() + command + ". Wpisz "
                + Configuration.getCommandPrefix() + "help " + command
                + ", aby uzyskać więcej informacji";

        return result;
    }
    
    public static <T> String convertListToString(List<T> list)
    {
        StringBuilder result = new StringBuilder("[");
        
        for (T el : list)
        {
            result.append(el.toString()).append(", ");
        }
        // delete last comma and space if there was some elements in list
        if (list.size() > 0)
        {
            result.delete(result.length() - 3, result.length() - 2);
        }
        result.append("]");
        
        return result.toString();
    }
}
