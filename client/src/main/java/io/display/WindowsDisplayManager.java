/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

import com.googlecode.lanterna.screen.Screen;

/**
 *
 * @author Robert
 */
public class WindowsDisplayManager extends AbstractLinuxWindowsDisplayManager
{

    // reference to screen that we share with WindowsNonBlockingInput class
    private final Screen screen;

    public WindowsDisplayManager(Screen screen, IDisplay display, IFormatter fformatter)
    {
        super(display, fformatter);
        this.screen = screen;
    }
    
    @Override
    protected void show(String toShow)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
