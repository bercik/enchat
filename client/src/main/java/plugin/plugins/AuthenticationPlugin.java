/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.plugins;

import plugin.IState;

/**
 *
 * @author robert
 */
public abstract class AuthenticationPlugin extends Plugin
{
    // TODO
    // login and registration should be in 4 steps with random byte array
    // given by server

    // state
    IState currentState = new State1();
    // login
    protected String login;
    // hash of password
    private String password;

    protected abstract void deliverError(int error);
    protected abstract void deliverPasswordHash(String passwordHash);

    @Override
    public void reset()
    {
        currentState = new State1();
        login = password = "";
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
            // zapisujemy login i hasło
            login = parameters[0];
            password = parameters[1];

            // wysyłamy paczkę do serwera
            pluginManager.send(id, new String[]{ login, password });

            // zwracamy kolejny stan
            return new State2();
        }
    }

    private class State2 implements IState
    {
        @Override
        public IState run(int error, String[] parameters)
        {
            // przesyłamy hasło do klasy dziedziczącej
            deliverPasswordHash(password);
            // przesyłamy id błędu do klasy dziedziczącej
            deliverError(error);

            // zwracamy pierwszy stan
            return new State1();
        }
    }
}
