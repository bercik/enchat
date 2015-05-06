/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Info;
import app_info.State;

/**
 *
 * @author robert
 */
public class StatePlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        State state = pluginManager.getAppState();
        Info info = Info.getInstance();
        
        String msg = "Stan aplikacji: " + state.toString();
        
        if (state == State.LOGGED)
        {
            msg += " jako " + info.getUserName();
        }
        else if (state == State.CONVERSATION)
        {
            msg += " " + info.getUserName() + " z " +
                    info.getInterlocutorName();
        }
        
        pluginManager.setMsg(msg, false);
    }
}
