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
public class ConnectPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        int controllerError = 0;
        String msg = "Połączono z serwerem";
        boolean connect = true;
        
        try
        {
            String mmsg = "Próbuję połączyć się z serwerem...";
            pluginManager.setMsg(mmsg, false);
            pluginManager.connect();
        }
        catch (Exception ex)
        {
            msg = "Nie udało połączyć się z serwerem";
            connect = false;
            controllerError = -1;
        }
        finally
        {
            pluginManager.setAppState(State.CONNECTED);
            pluginManager.updateControllerError(controllerError);
            pluginManager.setMsg(msg, !connect);
        }
    }
}
