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
    // kody
    private final FGColorCodes fgColorCodes = new FGColorCodes();
    private final BGColorCodes bgColorCodes = new BGColorCodes();
    private final SpecCodes specCodes = new SpecCodes();
    
    // ekran
    private final Screen screen;
    
    // informacje o formatowaniu
    private IFormatter.Color fgColor;
    private IFormatter.Color bgColor;
    private IFormatter.SpecialFormat specFormat;
    private boolean hasSpecFormat;
    
    public WindowsFormatterParser(Screen screen)
    {
        this.screen = screen;
    }
    
    private void defaultValues()
    {
        fgColor = IFormatter.Color.WHITE;
        bgColor = IFormatter.Color.BLACK;
        hasSpecFormat = false;
    }

    public void show(String text)
    {
        defaultValues();
        
        int i = 0;
        int row = 0;
        int col = 0;
        while (i < text.length())
        {
            char ch = text.charAt(i++);
            
            // jeżeli escape character, a zaraz po nim klamra otwierająca
            // to odczytujemy formatowanie
            if (ch == '\033' && text.charAt(i++) == '[')
            {
                String strCode = "";
                while (true)
                {
                    ch = text.charAt(i++);
                    if (ch == 'm')
                    {
                        break;
                    }
                    
                    strCode += ch;
                }
                
                int code = Integer.parseInt(strCode);
                // default values
                if (code == 0)
                {
                    defaultValues();
                }
                // inaczej jeżeli to kod koloru pierwszoplanowego
                else if (fgColorCodes.hasCode(code))
                {
                    fgColor = fgColorCodes.getColor(code);
                }
                // inaczej jeżeli to kod koloru tła
                else if (bgColorCodes.hasCode(code))
                {
                    bgColor = bgColorCodes.getColor(code);
                }   
                // inaczej jeżeli to kod specjalny
                else if (specCodes.hasCode(code))
                {
                    specFormat = specCodes.getSpecialFormat(code);
                    hasSpecFormat = true;
                }
                // inaczej błąd, bo powinniśmy obsługiwać wszystkie
                // kody formatowania
                else
                {
                    String msg = "Nieobsługiwany kod formatowania: " + strCode;
                    throw new RuntimeException(msg);
                }
            }
            // jeżeli znak nowej lini to przeskakujemy
            else if (ch == '\n')
            {
                ++row;
                col = 0;
            }
            // inaczej wyświetlamy jeden znak
            else
            {
                screen.putString(col++, row, Character.toString(ch),
                        Terminal.Color.WHITE, Terminal.Color.BLACK);
            }
        }
    }
}
