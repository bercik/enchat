/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Robert
 */
public class ExceptionUtils
{
    public static String getStackTraceString(Exception ex)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }
}
