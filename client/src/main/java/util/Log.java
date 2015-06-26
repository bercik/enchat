/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Robert
 */
public class Log
{
    private static final String DIR_PATH = "/data/";
    private static final String FILE_NAME = "log";

    // strong reference to Logger object
    private static final Logger logger = Logger.getLogger("MainLogger");
    
    // inicjalizacja loggerów
    public static void init() throws IOException
    {
        // wyłączamy logowanie na konsolę
        LogManager.getLogManager().reset();
        
        String loggerDirPath = new PathGetter().getAbsolutePathToJarDir()
                + DIR_PATH;
        String loggerFilePath = loggerDirPath + FILE_NAME;

        // tworzymy katalogi jeżeli nie istnieją
        File logDir = new File(loggerDirPath);
        logDir.mkdirs();
        // tworzymy uchwyt do pliku
        FileHandler fileHandler = new FileHandler(loggerFilePath);
        // dodajemy do loggera
        logger.addHandler(fileHandler);
        // tworzymy i ustawiamy proste formatowanie
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        // ustawiamy poziom dodawania logów
        logger.setLevel(Level.ALL);

        // dodajemy informację o rozpoczęciu zapisywania do logu
        logger.info("start logging");
    }
    
    // zamykamy pliki i czyścimy
    public static void close()
    {
        for (Handler h : logger.getHandlers())
        {
            h.close();
        }
    }
}
