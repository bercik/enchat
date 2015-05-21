/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import controller.ControllerManager;
import io.input.Key;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author robert
 */
public class CommandHistory
{
    private final List<String> commands = new LinkedList<>();
    private int currentCommand = 0;
    private final CommandLineController commandLineController;

    // klasa musi być w tej samej paczce co tworzący ją controller
    CommandHistory(CommandLineController ccommandLineController)
    {
        commandLineController = ccommandLineController;
    }

    public void addCommand(String command)
    {
        commands.add(command);
        currentCommand = commands.size();
    }

    public void putSpecialKey(Key key)
    {
        switch (key)
        {
            // strzałka w górę
            case ARROW_UP:
                arrowUp();
                break;
            // strzałka w dół
            case ARROW_DOWN:
                arrowDown();
                break;
        }
    }

    private void arrowUp()
    {
        if (commands.size() > 0)
        {
            if (currentCommand - 1 >= 0)
                --currentCommand;

            String currComm = commands.get(currentCommand);
            if (!currComm.equals(commandLineController.getCommand()))
                commandLineController.setCommand(currComm);
        }
    }

    private void arrowDown()
    {
        if (currentCommand + 1 < commands.size())
        {
            ++currentCommand;
            
            String currComm = commands.get(currentCommand);
            if (!currComm.equals(commandLineController.getCommand()))
                commandLineController.setCommand(currComm);
        }
        else
        {
            currentCommand = commands.size();
            if (!commandLineController.getCommand().equals(""))
                commandLineController.setCommand("");
        }
    }
}
