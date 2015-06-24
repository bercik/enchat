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
public class LinuxDisplayManager extends AbstractLinuxWindowsDisplayManager
{
    public LinuxDisplayManager(IDisplay display, IFormatter fformatter)
    {
        super(display, fformatter);
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

    @Override
    protected void show(String toShow)
    {
        // clear console (in fact move all console down height rows)
        clearConsole();
        // resize console in case that user did it
        setConsoleSize(Configuration.getWidth(), Configuration.getHeight());
        // show content to user
        System.out.print(toShow);
    }
}
