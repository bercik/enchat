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
package io.input;

import java.io.IOException;

/**
 *
 * @author robert
 */
public interface IInput
{
    public void init() throws IOException, InterruptedException;
    
    public char getChar();
    
    public boolean hasChar();
    
    public Key getSpecialKey();
    
    public boolean hasSpecialKey();
    
    public void update() throws IOException;
    
    public void close();
}
