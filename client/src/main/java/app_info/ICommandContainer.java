/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info;

/**
 *
 * @author robert
 */
public interface ICommandContainer
{
    public int getIdByString(String command);
    
    public String getCommandPrefix();
    
    public boolean hasPlugin(int id);
    
    public boolean hasController(int id);
    
    public boolean commandExists(String command);
    
    public boolean isCommand(String command);
    
    public boolean checkCommandAvailability(int id, State state);
}
