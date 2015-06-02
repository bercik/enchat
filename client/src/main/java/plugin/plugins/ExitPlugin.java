/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import app_info.Id;
import app_info.State;
import messages.MessageId;

/**
 *
 * @author robert
 */
public class ExitPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        // depends on app state we do diffrent things, but cascade
        // if you are in conversation u need to do all steps before exit 
        // application, if in connect state u only need do disconnect and exit
        State state = pluginManager.getAppState();

        switch (state)
        {
            case CONVERSATION:
                // we need to endtalk
                pluginManager.updatePlugin(
                        MessageId.END_TALK.getIntRepresentation(), 
                        new String[0]);
            case LOGGED:
                // we need to log out
                pluginManager.updatePlugin(
                        MessageId.LOGOUT.getIntRepresentation(), new String[0]);
            case CONNECTED:
                // we need to disconnect
                pluginManager.updatePlugin(
                        Id.DISCONNECT_PLUGIN.getIntRepresentation(),
                        new String[0]);
            case NOT_CONNECTED:
                // exit app
                pluginManager.setAppEnd();
                break;
            default:
                String msg = "Unknown app state " + state.toString();
                throw new RuntimeException(msg);
        }
    }
}