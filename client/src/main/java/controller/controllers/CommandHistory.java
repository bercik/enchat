/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import controller.ControllerManager;
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
    private ControllerManager controllerManager;
    private final CommandLineController commandLineController;

    // klasa musi być w tej samej paczce co tworzący ją controller
    CommandHistory(CommandLineController ccommandLineController)
    {
        commandLineController = ccommandLineController;
    }

    public void setControllerManager(ControllerManager ccontrollerManager)
    {
        controllerManager = ccontrollerManager;
    }

    public void addCommand(String command)
    {
        commands.add(command);
        currentCommand = commands.size();
    }

    public void putEscapeCharSequence(char[] escChSeq)
    {
        // strzałka w górę
        if (escChSeq[0] == 27 && escChSeq[1] == 91 && escChSeq[2] == 65)
        {
            arrowUp();
        }
        // strzałka w dół
        else if (escChSeq[0] == 27 && escChSeq[1] == 91 && escChSeq[2] == 66)
        {
            arrowDown();
        }
        // inaczej nie robimy nic
    }

    private void arrowUp()
    {
        if (commands.size() > 0)
        {
            if (currentCommand - 1 >= 0)
                --currentCommand;

            commandLineController.setCommand(commands.get(currentCommand));
        }
    }

    private void arrowDown()
    {
        if (currentCommand + 1 < commands.size())
        {
            ++currentCommand;
            commandLineController.setCommand(commands.get(currentCommand));
        }
        else
        {
            currentCommand = commands.size();
            commandLineController.setCommand("");
        }
    }
}
