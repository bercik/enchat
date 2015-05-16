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
public interface IState
{
    public IState run(int error, String[] parameters);
}
