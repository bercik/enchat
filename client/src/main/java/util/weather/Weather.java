/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.weather;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.bind.JAXBException;

/**
 *
 * @author robert
 */
public class Weather
{
    public Channel getWeather(String townName) throws CantFindWeatherException
    {
        try
        {
            String baseUrl = "http://query.yahooapis.com/v1/public/yql?q=";
            String query = 
                    "select woeid from geo.places where text=\"" + 
                    townName + "\"";
            String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8") + 
                    "&format=json";

            URL fullUrl = new URL(fullUrlStr);
            
            ResultObject resultObject = null;
            ResultArray resultArray = null;
            
            try (InputStream is = fullUrl.openStream(); 
                    InputStreamReader isr = new InputStreamReader(is); 
                    BufferedReader br = new BufferedReader(isr))
            {
                String result = "";
                String read;
                while ((read = br.readLine()) != null)
                {
                    result += read;
                }
                
                Gson gson = new Gson();
                try
                {
                    resultObject = gson.fromJson(result, ResultObject.class);
                }
                catch (com.google.gson.JsonSyntaxException ex)
                {
                    resultArray = gson.fromJson(result, ResultArray.class);
                }
            }
            
            Integer woeid = null;
            if (resultObject != null)
            {
                if (resultObject.query.results != null)
                {
                    woeid = resultObject.query.results.place.woeid;
                }
            }
            else if (resultArray != null)
            {
                woeid = resultArray.query.results.place[0].woeid;
            }
            
            if (woeid != null)
            {
                YahooWeatherService service = new YahooWeatherService();
                Channel channel = service.getForecast(woeid.toString(), 
                        DegreeUnit.CELSIUS);
                return channel;
            }
            else
            {
                throw new CantFindWeatherException();
            }
        }
        catch (IOException | JsonSyntaxException | JAXBException ex)
        {
            throw new CantFindWeatherException(ex);
        }
    }
    
    private static class ResultObject
    {
        public QueryObject query;
    }
    
    private static class ResultArray
    {
        public QueryArray query;
    }
    
    private static class QueryObject
    {
        public int count;
        public String created;
        public String lang;
        public ResultsObject results;
    }
    
    private static class QueryArray
    {
        public int count;
        public String created;
        public String lang;
        public ResultsArray results;
    }
    
    private static class ResultsObject
    {
        public Place place;
    }
    
    private static class ResultsArray
    {
        public Place[] place;
    }
    
    private static class Place
    {
        public int woeid;
    }
}
