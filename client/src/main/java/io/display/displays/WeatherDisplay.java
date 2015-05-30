/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.displays;

import com.github.fedy2.weather.data.Astronomy;
import com.github.fedy2.weather.data.Atmosphere;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.Wind;
import com.github.fedy2.weather.data.unit.Time;
import com.github.fedy2.weather.data.unit.WeekDay;
import io.display.IFormatter;
import java.util.List;

/**
 *
 * @author robert
 */
public class WeatherDisplay extends CommandLineDisplay
{
    private static final IFormatter.Color HEADER_COLOR = IFormatter.Color.BLUE;
    private static final IFormatter.Color FACTOR_COLOR = IFormatter.Color.YELLOW;
    private static final int SHOW_FORECASTS = 4;
    
    private final Channel channel;
    
    public WeatherDisplay(Channel cchannel)
    {
        channel = cchannel;
    }
    
    @Override
    protected String showBody()
    {
        String body = "";
        
        String header = formatter.spec(IFormatter.SpecialFormat.UNDERSCORE, 
                channel.getDescription());
        body += centerString(header) + "\n\n";
        
        // pobieramy różne klasy opisujące pogodę do lokalnych referencji
        Condition condition = channel.getItem().getCondition();
        Atmosphere atmosphere = channel.getAtmosphere();
        Astronomy astronomy = channel.getAstronomy();
        Wind wind = channel.getWind();
        
        // nagłówek dzisiaj
        body += showHeader("Dzisiaj:") + "\n";
        // temperatura
        body += showFactor("Temperatura", Integer.toString(condition.getTemp()), 
                "℃") + "\n";
        // ciśnienie
        body += showFactor("Ciśnienie", atmosphere.getPressure().toString(),
                "hPa") + "\n";
        // wilgotność
        body += showFactor("Wilgotność", atmosphere.getHumidity().toString(), 
                "%") + "\n";
        // wiatr
        body += showFactor("Prędkość wiatru", wind.getSpeed().toString(), 
                "km/h") + "\n";
        // opis
        body += showFactor("Opis", condition.getText(), "") + "\n";
        // wschód i zachód słońca
        body += showFactor("Wschód słońca", 
                showTime(astronomy.getSunrise()), "") + "\n";
        body += showFactor("Zachód słońca", 
                showTime(astronomy.getSunset()), "") + "\n\n";
        
        // prognoza pogody
        // pobieramy prognozy pogody
        List<Forecast> forecasts = channel.getItem().getForecasts();
        // wyświetlamy
        for (int i = 0; i < SHOW_FORECASTS; ++i)
        {
            // pobieramy prognozę bez dzisiejszej
            Forecast f = forecasts.get(i + 1);
            // header
            header = getDayName(f.getDay());
            body += showHeader(header) + "\n";
            // temperatura min-max
            String temp = Integer.toString(f.getLow()) + "-" + 
                    Integer.toString(f.getHigh());
            body += showFactor("Temperatura", temp, "℃") + "\n";
            // opis
            body += showFactor("Opis", f.getText(), "") + "\n\n";
        }
        
        // remove last two new line characters
        body = body.substring(0, body.length() - 2);
        
        // zwracamy do wyświetlenia
        return body;
    }
    
    private String showTime(Time time)
    {
        String result = Integer.toString(time.getHours()) + ":" + 
                Integer.toString(time.getMinutes()) + " " +
                time.getConvention().name();
        
        return result;
    }
    
    private String showHeader(String header)
    {
        String result = formatter.fg(HEADER_COLOR, header);
        
        return result;
    }
    
    private String showFactor(String factorName, String factor, String unit)
    {
        // maksymalny rozmiar factorName'a + wcięcie jakie chcemy uzyskać
        factorName = String.format("%17s", factorName);
        String result = formatter.spec(IFormatter.SpecialFormat.UNDERSCORE, 
                factorName);
        result += ": " + formatter.fg(FACTOR_COLOR, factor + " " + unit);
        
        return result;
    }
    
    private String getDayName(WeekDay weekDay)
    {
        switch (weekDay)
        {
            case MON:
                return "Poniedziałek:";
            case TUE:
                return "Wtorek:";
            case WED:
                return "Środa:";
            case THU:
                return "Czwartek:";
            case FRI:
                return "Piątek:";
            case SAT:
                return "Sobota:";
            case SUN:
                return "Niedziela:";
            default:
                throw new RuntimeException("Nieznany dzień: " + 
                        weekDay.toString());
        }
    }
}
