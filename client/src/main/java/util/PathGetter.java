/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Robert
 */
public class PathGetter
{
    public String getAbsolutePathToJarDir()
    {
        // pobieramy ścieżkę bezwzględną do pliku .jar
        String jarPath = PathGetter.class.getProtectionDomain().
                getCodeSource().getLocation().getPath();
        // jeżeli windows to usuwamy początkowy znak /
        if (System.getProperty("os.name").toLowerCase().startsWith("windows"))
        {
            jarPath = jarPath.substring(1);
        }
        // usuwamy nazwę pliku .jar, otrzymując ścięzkę do katalogu
        jarPath = jarPath.substring(0, jarPath.lastIndexOf('/'));
        // zwracamy
        return jarPath;
    }
}
