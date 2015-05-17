/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin;

/**
 *
 * @author robert
 */
public interface IPlugin
{
    public void reset();
    
    public void update(int error, String[] parameters);
    
    public void setPluginManager(PluginManager ppluginManager);
    
    public void setId(int iid);
    
    public int getId();
}
