/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info;

import app_info.exceptions.CommandAlreadyExistsException;
import app_info.exceptions.IdAlreadyExistsException;
import app_info.exceptions.NullCommandException;
import controller.IController;
import plugin.IPlugin;

import java.util.*;

/**
 *
 * @author robert
 */
public class CommandContainer implements IPluginCommandContainer, 
        IControllerCommandContainer, ICommandContainer
{
    private static final String COMMAND_PREFIX = "/";
    
    private final Map<String, Integer> sti = new HashMap<>();
    private final Map<Integer, IPlugin> itp = new HashMap<>();
    private final Map<Integer, IController> itc = new HashMap<>();
    private final Map<Integer, State[]> itps = new HashMap<>();
    
    public void registerCommand(int id, String command, IPlugin plugin, 
            IController controller, State[] possibleStates) 
    {
        // check for exceptions
        if (plugin == null && controller == null)
            throw new NullCommandException();
        
        if (sti.containsKey(command))
            throw new CommandAlreadyExistsException();
        
        if (itp.containsKey(id))
            throw new IdAlreadyExistsException();
        
        if (itc.containsKey(id))
            throw new IdAlreadyExistsException();
        
        // add to maps
        sti.put(command, id);
        
        if (plugin != null)
            itp.put(id, plugin);
        if (controller != null)
            itc.put(id, controller);
        
        itps.put(id, possibleStates);
    }
    
    public void registerPlugin(int id, IPlugin plugin, State[] possibleStates) 
    {
        // check for exceptions
        if (plugin == null)
            throw new NullCommandException();
        
        if (itp.containsKey(id))
            throw new IdAlreadyExistsException();
        
        if (itc.containsKey(id))
            throw new IdAlreadyExistsException();
        
        // add to maps
        itp.put(id, plugin);
        itps.put(id, possibleStates);
    }
    
    public void registerController(int id, IController controller,
            State[] possibleStates) 
    {
        // check for exceptions
        if (controller == null)
            throw new NullCommandException();
        
        if (itp.containsKey(id))
            throw new IdAlreadyExistsException();
        
        if (itc.containsKey(id))
            throw new IdAlreadyExistsException();
        
        // add to maps
        itc.put(id, controller);
        itps.put(id, possibleStates);
    }
    
    @Override
    public IPlugin[] getAllPlugins()
    {
        return itp.values().toArray(new IPlugin[0]);
    }

    @Override
    public IPlugin getPluginById(int id)
    {
        return itp.get(id);
    }

    @Override
    public int getIdByString(String command)
    {
        return sti.get(command);
    }

    @Override
    public boolean hasPlugin(int id)
    {
        return itp.containsKey(id);
    }

    @Override
    public boolean hasController(int id)
    {
        return itc.containsKey(id);
    }

    @Override
    public boolean commandExists(String command)
    {
        command = command.substring(COMMAND_PREFIX.length());
        return sti.containsKey(command);
    }

    @Override
    public boolean isCommand(String command)
    {
        return command.startsWith(COMMAND_PREFIX);
    }

    @Override
    public boolean checkCommandAvailability(int id, State state)
    {
        State[] possibleStates = itps.get(id);
        return Arrays.asList(possibleStates).contains(state);
    }

    @Override
    public IController[] getAllControllers()
    {
        return itc.values().toArray(new IController[0]);
    }

    @Override
    public IController getControllerById(int id)
    {
        return itc.get(id);
    }
}
