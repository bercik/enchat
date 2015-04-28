/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info;

import controller.IController;

/**
 *
 * @author robert
 */
public interface IControllerCommandContainer extends ICommandContainer
{
    public IController[] getAllControllers();
    
    public IController getControllerById(int id);
}
