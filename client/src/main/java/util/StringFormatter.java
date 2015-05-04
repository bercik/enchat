/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import app_info.Configuration;

/**
 *
 * @author robert
 */
public class StringFormatter
{
    public static String badCommand(String command)
    {
        Configuration conf = Configuration.getInstance();
        String result = "Złe wywołanie komendy " + conf.getCommandPrefix() +
                command + ". Wpisz " + conf.getCommandPrefix() + "help " + 
                command + ", aby uzyskać więcej informacji";
        
        return result;
    }
}
