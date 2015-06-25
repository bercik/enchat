/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
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
                // konwertujemy kolory formatera na kolory konsoli
                Terminal.Color termFGColor
                        = formatterColorToTerminalColor(fgColor);
                Terminal.Color termBGColor
                        = formatterColorToTerminalColor(bgColor);
                
                // jeżeli specjalny format (np. podkreślenie)
                if (hasSpecFormat)
                {
                    // konwertujemy specjalny format formatter na konsoli
                    ScreenCharacterStyle screenCharacterStyle
                            = specialFormatToScreenCharacterStyle(specFormat);
                    // wyświetlamy jeden znak
                    screen.putString(col++, row, Character.toString(ch),
                            termFGColor, termBGColor, screenCharacterStyle);
                }
                else
                {
                    // wyświetlamy jeden znak
                    screen.putString(col++, row, Character.toString(ch),
                            termFGColor, termBGColor);
                }
            }
        }
    }

    private Terminal.Color formatterColorToTerminalColor(IFormatter.Color color)
    {
        switch (color)
        {
            case BLACK:
                return Terminal.Color.BLACK;
            case BLUE:
                return Terminal.Color.BLUE;
            case CYAN:
                return Terminal.Color.CYAN;
            case GREEN:
                return Terminal.Color.GREEN;
            case MAGENTA:
                return Terminal.Color.MAGENTA;
            case RED:
                return Terminal.Color.RED;
            case WHITE:
                return Terminal.Color.WHITE;
            case YELLOW:
                return Terminal.Color.YELLOW;
            default:
                String msg = "Nieobsługiwany kolor " + color.toString();
                throw new RuntimeException(msg);
        }
    }

    private ScreenCharacterStyle specialFormatToScreenCharacterStyle(
            IFormatter.SpecialFormat specFormat)
    {
        switch (specFormat)
        {
            case DIM:
                return ScreenCharacterStyle.Bold;
            case UNDERSCORE:
                return ScreenCharacterStyle.Underline;
            default:
                String msg = "Nieobsługiwany specjalny format "
                        + specFormat.toString();
                throw new RuntimeException(msg);
        }
    }
}
