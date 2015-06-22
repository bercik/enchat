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
public class AnotherUserLoggedPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        pluginManager.setPasswordHash(null);
        pluginManager.setAppState(State.CONNECTED);
        String msg = "Ktoś inny zalogował się na Twoje konto. "
                + "Zostałeś wylogowany z serwera";
        pluginManager.setMsg(msg, false);
        pluginManager.updateControllerError(0);
    }
}
