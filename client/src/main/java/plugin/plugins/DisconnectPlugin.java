/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.State;

/**
 *
 * @author robert
 */
public class DisconnectPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        pluginManager.disconnect();
        pluginManager.setAppState(State.NOT_CONNECTED);
        String msg = "Rozłączono z serwerem";
        pluginManager.setMsg(msg, false);
        pluginManager.updateControllerError(0);
    }
}