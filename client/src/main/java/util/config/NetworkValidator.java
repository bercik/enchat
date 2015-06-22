/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author robert
 */
public class NetworkValidator
{

    public static boolean validateIPv4Address(String address)
    {
        String patternString
                = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    public static boolean validatePort(int port)
    {
        return port > 0 && port <= 65535;
    }

    public static boolean validatePort(String port)
    {
        try
        {
            int iPort = Integer.parseInt(port);
            
            return validatePort(iPort);
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }
}
