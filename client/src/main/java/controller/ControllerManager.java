/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app_info.IControllerCommandContainer;
import app_info.Id;
import app_info.State;
import controller.controllers.MainController;
import io.display.IDisplay;
import io.display.IDisplayManager;
import io.display.displays.ConnectedDisplay;
import io.display.displays.LoggedDisplay;
import io.display.displays.NonConnectedDisplay;
import java.io.IOException;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author robert
 */
public class ControllerManager
{
    // TODO
    // PLUGIN_MANAGER NEED TO BE ADD
    private final IControllerCommandContainer controllerCommandContainer;
    private State appState;
    private final IDisplayManager displayManager;
    private IController currentController;

    public ControllerManager(IDisplayManager ddisplayManager,
            IControllerCommandContainer ccontrollerCommandContainer)
    {
        // TODO 
        // pluginManager
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
        if (controllerCommandContainer.checkCommandAvailability(id, appState))
        {
            // pluginManager.startPlugin(id, parameters);
        }
        else
        {
            // MAYBE SHOULD BE CHANGED TO FOR EXAMPLE NOT THROWING EXCEPTION
            String msg = "Start pluginu o id=" + Integer.toString(id) + " nie"
                    + " może zostać wykonany przy stanie aplikacji "
                    + appState.toString();
            throw new RuntimeException(msg);
        }
    }

    public void setController(int id, String[] parameters)
    {
        // need to check if command with given id can do this in current app state
        if (controllerCommandContainer.checkCommandAvailability(id, appState))
        {
            currentController.reset();
            currentController
                    = controllerCommandContainer.getControllerById(id);
            currentController.update(parameters);
        }
        else
        {
            // MAYBE SHOULD BE CHANGED TO FOR EXAMPLE NOT THROWING EXCEPTION
            String msg = "Zmiana kontrolera o id=" + Integer.toString(id)
                    + " nie może zostać wykonana przy stanie aplikacji "
                    + appState.toString();
            throw new RuntimeException(msg);
        }
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
        // need to check if command with given id can do this in current app state
        if (controllerCommandContainer.checkCommandAvailability(id, appState))
        {
            displayManager.setDisplay(newDisplay, saveCommandLine);
        }
        else
        {
            // MAYBE SHOULD BE CHANGED TO FOR EXAMPLE NOT THROWING EXCEPTION
            String msg = "Zmiana ekranu przez komendę o id="
                    + Integer.toString(id) + " nie może zostać wykonana"
                    + " przy stanie aplikacji " + appState.toString();
            throw new RuntimeException(msg);
        }
    }

    public void setAppState(State newAppState)
    {
        // check if we dont change state to the same which is error
        if (appState.equals(newAppState))
        {
            String msg = "Zmiana stanu aplikacji w ControllerManager na taki "
                    + " sam: " + appState.toString();
            throw new RuntimeException(msg);
        }
        // change app state
        appState = newAppState;
        // reset previous controller
        currentController.reset();
        // change currentController to MainController
        currentController = controllerCommandContainer.getControllerById(
                Id.MAIN_CONTROLLER.getIntRepresentation());
        // change to proper display depends on app state
        switch (newAppState)
        {
            case NOT_CONNECTED:
                displayManager.setDisplay(new NonConnectedDisplay(), true);
                break;
            case CONNECTED:
                displayManager.setDisplay(new ConnectedDisplay(), true);
                break;
            case LOGGED:
                displayManager.setDisplay(new LoggedDisplay(), true);
                break;
            default:
                // do nothing. In case of Conversation display should be 
                // changed by proper plugin
                break;
        }
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
