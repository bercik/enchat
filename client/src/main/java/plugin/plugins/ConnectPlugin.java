/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

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
        try
        {
            pluginManager.connect();
        }
        catch (Exception ex)
        {
            String msg = "Nie udało połączyć się z serwerem";
            pluginManager.setMsg(msg, true);
            pluginManager.updateControllerError(-1);
        }
    }
}
