/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import controller.ControllerManager;

/**
 *
 * @author robert
 */
public class MainController extends CommandLineController
{
    public MainController(ControllerManager controllerManager)
    {
        super(controllerManager);
    }
    
    @Override
    public void updateError(int error)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(String[] parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
