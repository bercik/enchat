/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import com.github.fedy2.weather.data.Channel;
import io.display.displays.WeatherDisplay;
import util.StringFormatter;
import util.weather.CantFindWeatherException;
import util.weather.Weather;

/**
 *
 * @author robert
 */
public class WeatherPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        // updatujemy controller error
        pluginManager.updateControllerError(error);
        // sprawdzamy czy przekazano odpowiednią ilosć parametrów
        if (parameters.length == 0)
        {
            pluginManager.setMsg(StringFormatter.badCommand("weather"), true);
            return;
        }
        
        // pobieramy nazwę miasta do lokalnej referencji
        String townName = parameters[0];
        
        // pobieramy i wyświetlamy pogodę albo wiadomość o błędzie
        Weather weather = new Weather();
        try
        {
            Channel channel = weather.getWeather(townName);
            pluginManager.setDisplay(id, new WeatherDisplay(channel));
        }
        catch (CantFindWeatherException ex)
        {
            String msg = "Nie udało się odczytać pogody dla miasta " + townName;
            pluginManager.setMsg(msg, true);
        }
    }
}
