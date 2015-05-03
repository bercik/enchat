/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.help;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author robert
 */
public class HelpCommands
{
    private final Map<String, Command> stc = new HashMap<>();
    private final Map<String, Information> sti = new HashMap<>();
    
    public boolean hasCommand(String command)
    {
        return stc.containsKey(command);
    }
    
    public boolean hasInformation(String command)
    {
        return sti.containsKey(command);
    }
    
    public Command getCommand(String command)
    {
        return stc.get(command);
    }
    
    public Information getInformation(String command)
    {
        return sti.get(command);
    }
    
    public void addCommand(String name, Command command)
    {
        stc.put(name, command);
    }
    
    public void addInformation(String name, Information information)
    {
        sti.put(name, information);
    }
}
