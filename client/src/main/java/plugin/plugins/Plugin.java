/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import plugin.IPlugin;
import plugin.PluginManager;

/**
 *
 * @author robert
 */
public abstract class Plugin implements IPlugin
{
    protected int id;
    protected PluginManager pluginManager;

    @Override
    public final void setPluginManager(PluginManager ppluginManager)
    {
        pluginManager = ppluginManager;
    }
    
    @Override
    public final void setId(int iid)
    {
        id = iid;
    }

    @Override
    public final int getId()
    {
        return id;
    }
}
