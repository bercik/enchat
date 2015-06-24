/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import io.display.console_codes.BGColorCodes;
import io.display.console_codes.FGColorCodes;
import io.display.console_codes.SpecCodes;

/**
 *
 * @author Robert
 */
public class WindowsFormatterParser
{
    private final FGColorCodes fgColorCodes = new FGColorCodes();
    private final BGColorCodes bgColorCodes = new BGColorCodes();
    private final SpecCodes specCodes = new SpecCodes();
    
    private final Screen screen;

    public WindowsFormatterParser(Screen screen)
    {
        this.screen = screen;
    }

    public void show(String text)
    {
        int i = 0;
        int row = 0;
        int col = 0;
        while (i < text.length())
        {
            char ch = text.charAt(i);
            
            // jeżeli escape character to odczytujemy formatowanie
            if (ch == '\033')
            {
                ++i;
            }
            // jeżeli znak nowej lini to przeskakujemy
            else if (ch == '\n')
            {
                ++row;
                col = 0;
                ++i;
            }
            // inaczej wyświetlamy jeden znak
            else
            {
                screen.putString(col++, row, Character.toString(ch),
                        Terminal.Color.WHITE, Terminal.Color.BLACK);
                ++i;
            }
        }
    }
}
