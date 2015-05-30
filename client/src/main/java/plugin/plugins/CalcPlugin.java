/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.StringFormatter;

/**
 *
 * @author robert
 */
public class CalcPlugin extends Plugin
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
        // sprawdzamy czy przekazano parametr do komendy
        if (parameters.length == 0)
        {
            pluginManager.setMsg(StringFormatter.badCommand("calc"), true);
            return;
        }

        // rozdzielamy na liczbę, znak, liczbę
        String input = parameters[0];
        String patternText = "^([\\-]?\\d+)([+\\-*/\\^])([\\-]?\\d+)$";
        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(input);

        // sprawdzamy czy poprawne wejście
        if (matcher.find())
        {
            try
            {
                // pobieramy pierwszą liczbę
                double num1 = Integer.parseInt(matcher.group(1));
                // pobieramy operand
                char operand = matcher.group(2).charAt(0);
                // pobieramy drugą liczbę
                double num2 = Integer.parseInt(matcher.group(3));

                // obliczamy wynik
                Double result = calculate(num1, num2, operand);

                // jeżeli różny od null to wyświetlamy
                if (result != null)
                {
                    String msg = input + "=" + niceDoubleToString(result);
                    pluginManager.setMsg(msg, false);
                }
            }
            catch (NumberFormatException ex)
            {
                String msg = "Wpisane liczby są za duże";
                pluginManager.setMsg(msg, true);
            }
        }
        else
        {
            String msg = StringFormatter.badCommand("calc");
            pluginManager.setMsg(msg, true);
        }
    }

    private Double calculate(double num1, double num2, char operand)
    {
        Double result = 0.0;

        switch (operand)
        {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '^':
                result = Math.pow(num1, num2);
                break;
            case '/':
                if (num2 == 0)
                {
                    String msg = "Cholero nie dziel przez zero!";
                    pluginManager.setMsg(msg, true);
                    return null;
                }
                result = num1 / num2;
                break;
        }

        return result;
    }

    private String niceDoubleToString(double d)
    {
        if (d == (long)d)
        {
            return String.format("%d", (long)d);
        }
        else
        {
            return String.format("%s", d);
        }
    }
}
