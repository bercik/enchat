/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app_info.IControllerCommandContainer;
import app_info.State;
import controller.controllers.MainController;
import io.display.IDisplay;
import io.display.IDisplayManager;
import java.io.IOException;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author robert
 */
public class ControllerManager
{
    // PLUGIN_MANAGER NEED TO BE ADD
    private final IControllerCommandContainer controllerCommandContainer;
    private State appState;
    private final IDisplayManager displayManager;
    private IController currentController;

    public ControllerManager(IDisplayManager ddisplayManager,
            IControllerCommandContainer ccontrollerCommandContainer)
    {
        // unchanging references to some core objects
        controllerCommandContainer = ccontrollerCommandContainer;
        displayManager = ddisplayManager;
        // starting settings
        appState = State.NOT_CONNECTED;
        // this will be changed by CommandContainer get of
        // MainController instance
        currentController = new MainController(this);
    }

    public void startPlugin(int id, String[] parameters)
    {
        // TODO
        // need to check if command with given id can do this in current app state
    }

    public void setController(int id, String[] parameters)
    {
        // TODO
        // need to check if command with given id can do this in current app state
    }

    public void setCommand(String newCommand)
            throws IOException, GeneratingPublicKeyException
    {
        displayManager.setCommand(newCommand);
    }

    public void setMsg(String msg, boolean error)
            throws IOException, GeneratingPublicKeyException
    {
        displayManager.setMsg(msg, error);
    }

    public void setDisplay(int id, IDisplay newDisplay, boolean saveCommandLine)
    {
        // TODO
        // need to check if command with given id can do this in current app state
    }

    public void setAppState(State newAppState)
    {
        // TODO
        // change currentController to MainController and proper display
        // (depends on app state)
    }

    public String getCommand()
    {
        return displayManager.getCommand();
    }

    State getAppState()
    {
        return appState;
    }

    public void updateControllerError(int error)
    {
        currentController.updateError(error);
    }

    public void putChar(char ch)
    {
        currentController.putChar(ch);
    }
}
