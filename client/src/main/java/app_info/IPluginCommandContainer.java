/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info;

import plugin.IPlugin;

/**
 *
 * @author robert
 */
public interface IPluginCommandContainer extends ICommandContainer
{
    public IPlugin[] getAllPlugins();
    
    public IPlugin getPluginById(int id);
}
