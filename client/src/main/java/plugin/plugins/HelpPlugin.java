/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import io.display.displays.HelpDisplay;
import plugin.PluginManager;
import util.builder.HelpCommandsBuilder;
import util.help.Command;
import util.help.HelpCommands;
import util.help.Information;

/**
 *
 * @author robert
 */
public class HelpPlugin extends Plugin
{
    @Override
    public void reset()
    {
        // do nothing
    }

    @Override
    public void update(int error, String[] parameters)
    {
        if (parameters.length == 0)
        {
            // show default help display
            pluginManager.setDisplay(id, new HelpDisplay());
        }
        else
        {
            // get name which user want to check help
            String name = parameters[0];
            // get help commands object
            HelpCommands helpCommands = HelpCommandsBuilder.build();
            // check command exist for this name and if so display it
            if (helpCommands.hasCommand(name))
            {
                Command command = helpCommands.getCommand(name);
                pluginManager.setDisplay(id, new HelpDisplay(command));
            }
            // check information exist for this name and if so display it
            else if (helpCommands.hasInformation(name))
            {
                Information information = helpCommands.getInformation(name);
                pluginManager.setDisplay(id, new HelpDisplay(information));
            }
            // show error message
            else
            {
                String msg = "Komenda " + name + " dla /help nie istnieje";
                pluginManager.setMsg(msg, true);
            }
        }
    }
}
