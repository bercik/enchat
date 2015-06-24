/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

import app_info.Configuration;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;

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
    protected void show(String toShow, String command)
    {
        screen.clear();

        String[] split = toShow.split("\n");

        int col = 0;
        for (String line : split)
        {
            screen.putString(0, col++, line, Terminal.Color.BLUE,
                    Terminal.Color.BLACK, ScreenCharacterStyle.Underline);

        }

        screen.setCursorPosition(command.length(), 
                Configuration.getHeight() - 1);
        screen.refresh();
    }
}
