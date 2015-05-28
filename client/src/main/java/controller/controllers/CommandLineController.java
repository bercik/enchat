/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import controller.ControllerManager;
import controller.IController;
import io.input.Key;

/**
 *
 * @author robert
 */
public abstract class CommandLineController implements IController
{
    protected int id;
    protected ControllerManager controllerManager = null;

    private boolean showCommand;
    private boolean blockConsole;
    private boolean checkCharRange;
    private String command;
    private String commandToReturn;
    private String prefix;

    private static final int BACKSPACE = 127;
    private static final int ENTER = 10;
    private static final int MIN_CHAR = 32;
    private static final int MAX_CHAR = 2047;
    private static final int MAX_COMMAND_LENGTH = 120;

    public CommandLineController()
    {
        reset();
    }

    @Override
    public void putSpecialKey(Key key)
    {
        // jeżeli konsola nie jest zablokowana
        if (!blockConsole)
        {
            if (key == Key.DELETE)
            {
                // clear command
                setCommandPermanently("");
            }
        }
    }
    
    protected void setPrefix(String pprefix)
    {
        prefix = pprefix;
        displayCommand();
    }

    protected void setBlockConsole(boolean bblockConsole)
    {
        blockConsole = bblockConsole;
    }

    protected void setCheckCharRange(boolean ccheckCharRange)
    {
        checkCharRange = ccheckCharRange;
    }

    /**
     * Funkcja wywoływana gdy użytkownik naciśnie klawisz ENTER. Wewnątrz
     * funkcji powinno znaleźć się wywołanie metody setCommand z odpowiednim
     * parametrem (np. jeżeli chcemy, aby po wciśnięcu ENTER pole komendy było
     * czyszczone powinniśmy przekazać parametr pusty string).
     *
     * @param input Komenda po wciśnięciu klawisza ENTER.
     */
    protected abstract void route(String input);

    protected void setShowCommand(boolean sshowCommand)
    {
        showCommand = sshowCommand;
        displayCommand();
    }

    protected void setCommand(String newCommand)
    {
        command = newCommand;
        displayCommand();
    }

    protected void setCommandPermanently(String newCommand)
    {
        command = commandToReturn = newCommand;
        displayCommand();
    }
    
    @Override
    public void setControllerManager(ControllerManager ccontrollerManager)
    {
        controllerManager = ccontrollerManager;
    }

    @Override
    public final String getCommand()
    {
        return (showCommand == true) ? commandToReturn : "";
    }

    @Override
    public void setId(int iid)
    {
        id = iid;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public final void reset()
    {
        showCommand = true;
        blockConsole = false;
        checkCharRange = true;
        command = "";
        commandToReturn = "";
        prefix = "";
    }
    
    @Override
    public final void putChar(char ch)
    {
        if (!blockConsole)
        {
            boolean dispCommand = false;

            if (ch == ENTER)
            {
                commandToReturn = command;
                command = "";
                route(commandToReturn);
                dispCommand = false;
            }
            else if (ch == BACKSPACE && command.length() > 0)
            {
                command = command.substring(0, command.length() - 1);
                dispCommand = true;
            }
            else if (ch != BACKSPACE)
            {
                int wholeCommandLength = prefix.length() + command.length();

                if (wholeCommandLength < MAX_COMMAND_LENGTH)
                {
                    if (checkCharRange)
                    {
                        if (ch >= MIN_CHAR && ch <= MAX_CHAR)
                        {
                            command += ch;
                            dispCommand = true;
                        }
                    }
                    else
                    {
                        command += ch;
                        dispCommand = true;
                    }
                }
            }

            commandToReturn = command;

            if (dispCommand)
            {
                displayCommand();
            }
        }
    }

    private void displayCommand()
    {
        String newCommand = prefix;

        if (showCommand)
        {
            newCommand += command;
        }

        controllerManager.setCommand(newCommand);
    }
    
    protected boolean getBlockConsole()
    {
        return blockConsole;
    }
}
