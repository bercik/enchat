/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.input;

import java.io.IOException;

/**
 *
 * @author Robert
 */
public class WindowsNonBlockingInput implements IInput
{
    // TODO
    
    @Override
    public void init() throws IOException, InterruptedException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public char getChar()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasChar()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Key getSpecialKey()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasSpecialKey()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update() throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
