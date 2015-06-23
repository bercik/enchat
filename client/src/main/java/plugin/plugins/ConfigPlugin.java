/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Configuration;
import java.io.IOException;
import util.StringFormatter;
import util.config.NetworkValidator;

/**
 *
 * @author robert
 */
public class ConfigPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        // aktualizujemy controller error
        pluginManager.updateControllerError(error);
     
        // sprawdzamy czy została przekazana odpowiednia ilość parametrów
        if (parameters.length < 2)
        {
            pluginManager.setMsg(StringFormatter.badCommand("config"), true);
            return;
        }
        
        // pobieramy adres i port do lokalnych referencji
        String serverAddress = parameters[0];
        String port = parameters[1];
        
        // sprawdzamy poprawność adresu IP
        if (!NetworkValidator.validateIPv4Address(serverAddress))
        {
            String msg = "Niepoprawny adres IP: " + serverAddress;
            pluginManager.setMsg(msg, true);
            
            return;
        }
        
        // sprawdzamy poprawność portu
        if (!NetworkValidator.validatePort(port))
        {
            String msg = "Niepoprawny port: " + port;
            pluginManager.setMsg(msg, true);
            
            return;
        }
        
        // ustawiamy w klasie konfigurującej nowe wartości
        Configuration conf = Configuration.getInstance();
        try
        {
            conf.SetAndSaveToFile(serverAddress, Integer.parseInt(port));
        }
        catch (IOException ex)
        {
            String msg = "Nie udało się zapisać do pliku, ale adres " +
                    serverAddress + " i port " + port + " zostaną użyte w tej "
                    + "sesji aplikacji";
            pluginManager.setMsg(msg, true);
        }
        
        // informujemy, że zostaną użyte przy ponownej próbie połączenia
        String msg = "Udało się zapisać. Nowy adres " + serverAddress + 
                " i port " + port +
                " zostaną użyte przy następnej próbie połączenia z serwerem";
        pluginManager.setMsg(msg, false);
    }
}
