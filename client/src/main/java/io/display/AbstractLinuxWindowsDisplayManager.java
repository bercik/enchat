/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

import app_info.Configuration;

/**
 *
 * @author Robert
 */
public abstract class AbstractLinuxWindowsDisplayManager
        implements IDisplayManager
{
    private IDisplay currentDisplay = null;
    private final IFormatter formatter;

    public AbstractLinuxWindowsDisplayManager(
            IDisplay display, IFormatter fformatter)
    {
        currentDisplay = display;
        currentDisplay.setFormatter(fformatter);
        formatter = fformatter;
    }

    @Override
    public void init()
    {
        refresh();
    }
    
    @Override
    public void setMsg(String msg, boolean error)
    {
        msg = trimString(msg, Configuration.getWidth());
        
        // na wszelki wypadek sprawdzamy, chociaż poprzednia metoda powinna
        // odpowiednio przyciąć msg
        if (msg.length() > Configuration.getWidth())
        {
            String errorMsg = "Wiadomość " + msg + " w setMsg dłuższa niż " +
                    "szerokość konsoli";
            throw new RuntimeException(errorMsg);
        }

        currentDisplay.setMsg(msg, error);
        refresh();
    }

    @Override
    public void setCommand(String newCommand)
    {
        if (newCommand.length() > Configuration.getWidth())
        {
            String errorMsg = "Komenda " + newCommand + " w setCommand "
                    + "dłuższa niż szerokość konsoli";
            throw new RuntimeException(errorMsg);
        }

        currentDisplay.setCommand(newCommand);
        refresh();
    }

    @Override
    public void setDisplay(IDisplay newDisplay)
    {
        newDisplay.setFormatter(formatter);
        currentDisplay = newDisplay;
        refresh();
    }

    @Override
    public String getCommand()
    {
        return currentDisplay.getCommand();
    }

    protected abstract void show(String toShow, String command);
    
    private void refresh()
    {
        // get content to show and validate it
        String toShow = currentDisplay.show();
        validateShow(toShow);
        // call function implemented by subclasses which shows content to user
        show(toShow, currentDisplay.getCommand());
    }

    private void validateShow(String show)
    {
        String[] split = show.split("\n");

        int i = 1;
        for (String str : split)
        {
            // WARNING
            // we remove characters used for formatting text 
            // (they aren't visible). This need to be change if you want
            // to port program to another platofrm
            str = str.replaceAll("\033\\[\\d+m", "");

            if (str.length() > Configuration.getWidth())
            {
                String msg = "Linia " + i + ":" + str + " w displayu "
                        + currentDisplay.getClass().getSimpleName() + " jest za"
                        + " długa";
                throw new RuntimeException(msg);
            }

            ++i;
        }
    }

    private String trimString(String str, int length)
    {
        String postfix = "...";
        
        if (str.length() > length)
        {
            str = str.substring(0, length - postfix.length());
            str += postfix;
        }
        
        return str;
    }
}
