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
package io;

import io.display.IDisplayManager;
import io.display.IDisplay;
import io.display.IFormatter;
import io.display.LinuxDisplayManager;
import io.display.LinuxFormatter;
import io.display.displays.NonConnectedDisplay;
import io.input.IInput;
import io.input.LinuxNonBlockingInput;
import java.io.IOException;

/**
 *
 * @author robert
 */
public class IOSetFabric
{
    public static IOSet getIOSet() throws NotSupportedOsException,
            IOException, InterruptedException
    {
        String os = System.getProperty("os.name");
        os = os.toLowerCase();
        
        IDisplayManager displayManager = null;
        IInput input = null;
        
        if (os.equals("linux"))
        {
            input = new LinuxNonBlockingInput();
            IDisplay defaultDisplay = new NonConnectedDisplay();
            IFormatter formatter = new LinuxFormatter();
            displayManager = new LinuxDisplayManager(defaultDisplay, formatter);
        }
        else
        {
            throw new NotSupportedOsException(os);
        }
        
        return new IOSet(displayManager, input);
    }
}
