/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import rsa.RSA;

/**
 *
 * @author robert
 */
public class Hash
{
    public static String createPasswordHash(String password, String login)
    {
        try
        {
            // solimy hasło loginem
            password += login;
            // tworzymy skrót hasła (dla bezpieczeństwa)
            byte[] passwordHash = RSA.hash(
                    password.getBytes(RSA.STRING_CODING));
            password = new String(passwordHash, RSA.STRING_CODING);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Kodowanie " + RSA.STRING_CODING
                    + " jest złe", e);
        }
        
        return password;
    }
}
