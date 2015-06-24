/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import app_info.Configuration;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.swing.TerminalPalette;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Robert
 */
public class WindowsScreenFabric
{
    public Screen createScreen()
    {
        SwingTerminal terminal = new SwingTerminal(
                Configuration.getWidth(), Configuration.getHeight());
        terminal.setTerminalPalette(TerminalPalette.DEFAULT);

        Screen screen = TerminalFacade.createScreen(terminal);
        screen.startScreen();

        JFrame frame = terminal.getJFrame();
        frame.setTitle("enChat");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        
        return screen;
    }
}
