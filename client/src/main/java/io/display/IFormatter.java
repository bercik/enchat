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

/**
 *
 * @author robert
 */
public interface IFormatter
{
    /**
        * Enum with colors that we are using to change formating.
        */
    public enum Color
    {
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        MAGENTA,
        CYAN,
        WHITE
    };
    
    /**
        * Enum with special formats (without colors).
        */
    public enum SpecialFormat
    {
        DIM,
        UNDERSCORE
    };
    
    public String fg(Color color, String target);
    
    public String bg(Color color, String target);
    
    public String spec(SpecialFormat sf, String target);
}
