/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.ICommandContainer;
import app_info.Id;
import app_info.State;
import controller.ControllerManager;
import io.display.displays.ConnectedDisplay;
import io.display.displays.HelpDisplay;

/**
 *
 * @author robert
 */
public class MainController extends CommandLineController
{
    public MainController(ICommandContainer commandContainer)
    {
        super();
    }

    @Override
    protected void route(String input)
    {
        // TODO
        
        switch (input)
        {
            case "/help":
                controllerManager.setDisplay(id, new HelpDisplay(), false);
                break;
            case "/connect":
                controllerManager.setAppState(State.CONNECTED);
                break;
            case "/test1":
            case "/test2":
                controllerManager.setController(-10, new String[] { "test" });
                break;
        }
    }

    @Override
    public void start(String previousCommand, String[] parameters)
    {
        // ustawiamy na poprzednią komendę dzięki czemu uzyskujemy efekt
        // płynnego przejścia między controllerami
        setCommand(previousCommand);
    }

    @Override
    public void updateError(int error)
    {
        // do nothing
    }
}
