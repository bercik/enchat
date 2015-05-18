/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app_info.IControllerCommandContainer;
import app_info.Id;
import app_info.State;
import io.display.IDisplay;
import io.display.IDisplayManager;
import io.display.displays.ConnectedDisplay;
import io.display.displays.LoggedDisplay;
import io.display.displays.NonConnectedDisplay;
import io.input.Key;
import plugin.PluginManager;

/**
 *
 * @author robert
 */
public class ControllerManager
{
    // zmienna informująca czy mamy zakończyć działanie aplikacji
    private boolean appEnd = false;
    private final IControllerCommandContainer controllerCommandContainer;
    private State appState;
    private final IDisplayManager displayManager;
    private IController currentController;
    private final PluginManager pluginManager;

    public ControllerManager(IDisplayManager ddisplayManager,
            IControllerCommandContainer ccontrollerCommandContainer,
            PluginManager ppluginManager)
    {
        // unchanging references to some core objects
        pluginManager = ppluginManager;
        controllerCommandContainer = ccontrollerCommandContainer;
        displayManager = ddisplayManager;
        // starting settings
        appState = State.NOT_CONNECTED;
        // this will be changed by CommandContainer get of
        // MainController instance
        currentController = controllerCommandContainer.getControllerById(
                Id.MAIN_CONTROLLER.getIntRepresentation());
        
        // update controller manager reference in controllers
        for (IController controller : 
                controllerCommandContainer.getAllControllers())
        {
            controller.setControllerManager(this);
        }
    }
    
    public boolean isAppEnd()
    {
        return appEnd;
    }
    
    public void setAppEnd()
    {
        appEnd = true;
    }

    public void startPlugin(int id, String[] parameters)
    {
        // need to check if command with given id can do this in current app state
        if (controllerCommandContainer.checkCommandAvailability(id, appState))
        {
            pluginManager.updatePlugin(id, parameters);
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
            String previousCommand = currentController.getCommand();
            currentController.reset();
            currentController
                    = controllerCommandContainer.getControllerById(id);
            currentController.start(previousCommand, parameters);
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
    {
        displayManager.setCommand(newCommand);
    }

    public void setMsg(String msg, boolean error)
    {
        displayManager.setMsg(msg, error);
    }

    public void setDisplay(int callerId, IDisplay newDisplay)
    {
        // need to check if command with given id can do this in current app state
        if (controllerCommandContainer.checkCommandAvailability(callerId, appState))
        {
            displayManager.setDisplay(newDisplay);
        }
        else
        {
            // MAYBE SHOULD BE CHANGED TO FOR EXAMPLE NOT THROWING EXCEPTION
            String msg = "Zmiana ekranu przez komendę o id="
                    + Integer.toString(callerId) + " nie może zostać wykonana"
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
        // change controller
        setController(Id.MAIN_CONTROLLER.getIntRepresentation(), null);
        // change to proper display depends on app state
        switch (newAppState)
        {
            case NOT_CONNECTED:
                displayManager.setDisplay(new NonConnectedDisplay());
                break;
            case CONNECTED:
                displayManager.setDisplay(new ConnectedDisplay());
                break;
            case LOGGED:
                displayManager.setDisplay(new LoggedDisplay());
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

    public State getAppState()
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
    
    public void putSpecialKey(Key key)
    {
        currentController.putSpecialKey(key);
    }
}
