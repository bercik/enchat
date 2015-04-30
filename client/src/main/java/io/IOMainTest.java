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

import app_info.Info;
import io.display.IDisplayManager;
import io.display.displays.ConnectedDisplay;
import io.display.displays.LoggedDisplay;
import io.display.displays.NonConnectedDisplay;
import io.input.IInput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author robert
 */
public class IOMainTest
{
    public static void main(String[] args)
    {
        IOSet ioSet = null;
        try
        {
            ioSet = IOSetFabric.getIOSet();
            IDisplayManager displayManager = ioSet.getDisplayManager();
            IInput input = ioSet.getInput();
            
            while (true)
            {
                input.update();
                if (input.hasChar())
                {
                    char ch = input.getChar();
                    
                    if (ch == 27)
                    {
                        break;
                    }
                    else if (ch == 10)
                    {
                        String nick = "bercik";
                        Info.getInstance().setUserName(nick);
                        displayManager.setMsg("Zalogowany jako " + nick, false);
                        displayManager.setDisplay(
                                new LoggedDisplay(), true);
                    }
                    else if (ch == 127)
                    {
                        String command = displayManager.getCommand();
                        if (command.length() > 0)
                        {
                            command = command.substring(0, command.length() - 1);
                        }
                        displayManager.setCommand(command);
                    }
                    else
                    {
                        String command = displayManager.getCommand();
                        command += ch;
                        displayManager.setCommand(command);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (ioSet != null)
                ioSet.getInput().close();
        }
    }
}
