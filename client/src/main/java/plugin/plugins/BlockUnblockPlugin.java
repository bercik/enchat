/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import messages.MessageId;
import plugin.IState;
import util.Authentication;
import util.StringFormatter;

/**
 *
 * @author robert
 */
public abstract class BlockUnblockPlugin extends Plugin
{
    private final String commandName;
    
    private String username;
    private IState currentState = new State1();

    protected String getUsername()
    {
        return username;
    }
    
    public BlockUnblockPlugin(String commandName)
    {
        this.commandName = commandName;
    }
    
    protected abstract void deliverError(int error);
    
    @Override
    public void reset()
    {
        currentState = new State1();
    }

    @Override
    public void update(int error, String[] parameters)
    {
        currentState = currentState.run(error, parameters);
    }
    
    private class State1 implements IState
    {
        @Override
        public IState run(int error, String[] parameters)
        {
            // sprawdzamy czy podano username
            if (parameters.length > 0)
            {
                username = parameters[0];
                // sprawdzamy czy wpisany login jest poprawny
                if (!Authentication.isLoginCorrect(username))
                {
                    // wyświetlamy informację o źle wpisanym loginie
                    String msg = "Login " + username + " jest nieprawidłowy";
                    pluginManager.setMsg(msg, true);
                    return new State1();
                }
                
                // wysyłamy żądanie zablokowania użytkownika
                pluginManager.send(id, new String[]{ username });
                
                // zwracamy kolejny stan
                return new State2();
            }
            else
            {
                // wyświetlamy informacje o błędzie
                String msg = StringFormatter.badCommand(commandName);
                pluginManager.setMsg(msg, true);
                
                // zwracamy pierwszy stan
                return new State1();
            }
        }
    }
    
    private class State2 implements IState
    {
        @Override
        public IState run(int error, String[] parameters)
        {
            // przesyłamy id błędu do klasy dziedziczącej
            deliverError(error);
            
            // zwracamy pierwszy stan
            return new State1();
        }
    }
}