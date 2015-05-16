/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import io.display.IDisplay;
import io.display.displays.UsersListDisplay;
import plugin.IState;

/**
 *
 * @author robert
 */
public class UsersListPlugin extends Plugin
{
    private final String header;
    private IState currentState = new State1();

    public UsersListPlugin(String hheader)
    {
        header = hheader;
    }

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
            // wysyłamy prośbę o listę użytkowników do serwera
            pluginManager.send(id, new String[0]);

            // zwraacmy kolejny stan
            return new State2();
        }
    }

    private class State2 implements IState
    {
        @Override
        public IState run(int error, String[] parameters)
        {
            // wyświetlamy listę użytkowników
            IDisplay display = new UsersListDisplay(header, parameters);

            pluginManager.setDisplay(id, display);

            // przekazujemy error do controller
            pluginManager.updateControllerError(error);

            // zwracamy pierwszy stan
            return new State1();
        }
    }
}
