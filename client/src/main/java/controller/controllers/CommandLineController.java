/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import controller.ControllerManager;
import controller.IController;

/**
 *
 * @author robert
 */
public abstract class CommandLineController implements IController
{
    private ControllerManager controllerManager;
    
    public CommandLineController(ControllerManager ccontrollerManager)
    {
        // TODO
        controllerManager = ccontrollerManager;
    }

    @Override
    public abstract void updateError(int error);

    @Override
    public abstract void update(String[] parameters);

    @Override
    public final void putChar(char ch)
    {
        // TODO
    }
    
}
