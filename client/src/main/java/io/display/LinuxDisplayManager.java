/*
 * Copyright (C) 2015 robert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.display;

import app_info.Configuration;

/**
 *
 * @author robert
 */
public class LinuxDisplayManager implements IDisplayManager
{
    private IDisplay currentDisplay = null;
    private final IFormatter formatter;

    public LinuxDisplayManager(IDisplay display, IFormatter fformatter)
    {
        currentDisplay = display;
        currentDisplay.setFormatter(fformatter);
        formatter = fformatter;
        refresh();
    }

    @Override
    public void setMsg(String msg, boolean error)
    {
        Configuration conf = Configuration.getInstance();
        if (msg.length() > conf.getWidth())
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
        Configuration conf = Configuration.getInstance();
        if (newCommand.length() > conf.getWidth())
        {
            String errorMsg = "Komenda " + newCommand + " w setCommand "
                    + "dłuższa niż szerokość konsoli";
            throw new RuntimeException(errorMsg);
        }
        
        currentDisplay.setCommand(newCommand);
        refresh();
    }

    @Override
    public void setDisplay(IDisplay newDisplay, boolean saveCommandLine)
    {
        newDisplay.setFormatter(formatter);
        if (saveCommandLine)
        {
            newDisplay.setCommand(currentDisplay.getCommand());
            newDisplay.setMsg(currentDisplay.getMsg());
        }
        currentDisplay = newDisplay;
        refresh();
    }

    @Override
    public String getCommand()
    {
        return currentDisplay.getCommand();
    }

    private void clearConsole()
    {
        // Linux hack to clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void setConsoleSize(int width, int height)
    {
        String toPrint = "\033[8;" + Integer.toString(height)
                + ";" + Integer.toString(width) + "t";
        System.out.print(toPrint);
    }

    private void refresh()
    {
        clearConsole();
        // get console size from configuration class
        Configuration conf = Configuration.getInstance();
        setConsoleSize(conf.getWidth(), conf.getHeight());
        // show content to user
        System.out.print(currentDisplay.show());
    }
}
