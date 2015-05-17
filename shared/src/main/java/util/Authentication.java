/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author robert
 */
public class Authentication
{
    public final static int MIN_LOGIN_LENGTH = 4;
    public final static int MAX_LOGIN_LENGTH = 8;

    public final static int MIN_PASSWORD_LENGTH = 4;
    public final static int MAX_PASSWORD_LENGTH = 12;

    public static boolean isLoginCorrect(String login)
    {
        // na początku dopuszczamy tylko litery małe lub duże
        // potem litery małe, duże, cyfry i znak podkreślenia
        String pattern = "^[a-zA-Z]{1}\\w{"
                + Integer.toString(MIN_LOGIN_LENGTH - 1) + ","
                + Integer.toString(MAX_LOGIN_LENGTH - 1) + "}$";

        return login.matches(pattern);
    }

    public static boolean isPasswordCorrect(String password)
    {
        return (password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH);
    }
}
