/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.input;

import com.googlecode.lanterna.screen.Screen;
import controller.controllers.CommandLineController;
import java.io.IOException;

/**
 *
 * @author Robert
 */
public class WindowsNonBlockingInput implements IInput
{
    // TODO
    // referencja do ekranu(dzielona z WindowsDisplayManager)
    private final Screen screen;

    // znak
    private char ch;
    // czy mamy znak
    private boolean hasCh = false;
    // specjalny klawisz (np. ESC, ARROW_UP, ...)
    private Key specialKey;
    // czy mamy specjalny klawisz
    private boolean hasSpecialKey = false;

    public WindowsNonBlockingInput(Screen screen)
    {
        this.screen = screen;
    }

    @Override
    public void init() throws IOException, InterruptedException
    {
        // do nothing
    }

    @Override
    public char getChar()
    {
        hasCh = false;
        return ch;
    }

    @Override
    public boolean hasChar()
    {
        return hasCh;
    }

    @Override
    public Key getSpecialKey()
    {
        hasSpecialKey = false;
        return specialKey;
    }

    @Override
    public boolean hasSpecialKey()
    {
        return hasSpecialKey;
    }

    @Override
    public void update() throws IOException
    {
        // pobieramy klawisz (jeśli nie ma zostanie zwrócone NULL)
        com.googlecode.lanterna.input.Key key = screen.readInput();

        // sprawdzamy czy wciśnięto jakiś klawisz
        if (key != null)
        {
            // TODELETE
            System.out.println(key.getKind().toString());

            // sprawdzamy rodzaj wciśniętego klawisza
            switch (key.getKind())
            {
                // characters
                // dla poniższych przypisujemy konkretny kod
                // znajdujący się w klasie CommandLineController
                case Tab:
                    ch = CommandLineController.TAB;
                    hasCh = true;
                    break;
                case Enter:
                    ch = CommandLineController.ENTER;
                    hasCh = true;
                    break;
                case Backspace:
                    ch = CommandLineController.BACKSPACE;
                    hasCh = true;
                    break;
                case NormalKey:
                    // pobieramy znak
                    Character inputCh = key.getCharacter();
                    // sprawdzamy czy znak ASCII
                    // na windowsie nie dopuszczamy innych znaków
                    // jeżeli wciśnięty ALT to nie pobieramy
                    if (inputCh >= 32 && inputCh <= 126 && !key.isAltPressed())
                    {
                        ch = inputCh;
                        hasCh = true;
                    }
                    break;
                // special keys
                // dla poniższych przypisujemy specjalny znak
                case Escape:
                    specialKey = Key.ESC;
                    hasSpecialKey = true;
                    break;
                case ArrowUp:
                    specialKey = Key.ARROW_UP;
                    hasSpecialKey = true;
                    break;
                case ArrowDown:
                    specialKey = Key.ARROW_DOWN;
                    hasSpecialKey = true;
                    break;
                case Delete:
                    specialKey = Key.DELETE;
                    hasSpecialKey = true;
                    break;
            }
        }
    }

    @Override
    public void close()
    {
        // zamykamy ekran
        screen.stopScreen();
    }
}
