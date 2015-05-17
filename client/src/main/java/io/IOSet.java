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
import io.input.IInput;

/**
 *
 * @author robert
 */
public class IOSet
{
    private final IDisplayManager displayManager;
    private final IInput input;

    IOSet(IDisplayManager ddisplayManager, IInput iinput)
    {
        this.displayManager = ddisplayManager;
        this.input = iinput;
    }

    public IDisplayManager getDisplayManager()
    {
        return displayManager;
    }

    public IInput getInput()
    {
        return input;
    }
}
