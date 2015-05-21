/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.Id;
import io.input.Key;
import messages.MessageId;
import util.Authentication;
import util.Hash;
import util.StringFormatter;
import util.exceptions.RunMethodNotImplementedException;

/**
 *
 * @author robert
 */
public class RegisterController extends CommandLineController
{
    private IState currentState;

    private String login;
    private String firstPassword;

    @Override
    public void putSpecialKey(Key key)
    {
        // wywołujemy metodę klasy bazowej
        super.putSpecialKey(key);
        // jeżeli escape to wracamy do main controller
        if (key == Key.ESC)
        {
            controllerManager.setController(
                Id.MAIN_CONTROLLER.getIntRepresentation(), null);
            controllerManager.setMsg("", true);
        }
    }

    @Override
    protected void route(String input)
    {
        currentState = currentState.run(input);
    }

    @Override
    public void start(String previousCommand, String[] parameters)
    {
        currentState = new State1();
        currentState = currentState.run(previousCommand, parameters);
    }

    @Override
    public void updateError(int error)
    {
        // zmieniamy na MainController
        controllerManager.setController(
            Id.MAIN_CONTROLLER.getIntRepresentation(), null);
    }

    private class State1 implements IState
    {
        @Override
        public IState run(String param)
        {
            throw new RunMethodNotImplementedException();
        }

        @Override
        public IState run(String param1, String[] parameters)
        {
            String previousCommand = param1;
            // sprawdzamy czy login został wpisany
            if (parameters.length < 1)
            {
                controllerManager.setMsg(StringFormatter.badCommand("register"),
                        true);
                controllerManager.setController(
                        Id.MAIN_CONTROLLER.getIntRepresentation(), null);
                return new State1();
            }
            // sprawdzamy poprawność loginu
            if (!Authentication.isLoginCorrect(parameters[0]))
            {
                String msg = "Nieprawidłowy login " + parameters[0]
                        + ". Prawidłowy ma "
                        + Integer.toString(Authentication.MIN_LOGIN_LENGTH) + "-"
                        + Integer.toString(Authentication.MAX_LOGIN_LENGTH)
                        + " znaków i składa się z samych liter, cyfr lub"
                        + " znaku podkreślenia";
                controllerManager.setMsg(msg, true);
                controllerManager.setController(
                        Id.MAIN_CONTROLLER.getIntRepresentation(), null);
                return new State1();
            }

            // zapisujemy tymczasowo login
            login = parameters[0];
            // ustawiamy wiadomość na poprzednią komendę
            controllerManager.setMsg(previousCommand, false);
            // ustawiamy prefix i nie wyświetlanie wpisywanej treści
            setPrefix("Podaj hasło:");
            setShowCommand(false);
            // w haśle można wpisać dowolne znaki
            setCheckCharRange(false);
            // zwracamy kolejny stan
            return new State2();
        }
    }

    private class State2 implements IState
    {
        @Override
        public IState run(String param)
        {
            String password = param;
            // sprawdzamy poprawność hasła
            if (!Authentication.isPasswordCorrect(password))
            {
                String msg = "Zbyt krótkie lub zbyt długie hasło. "
                        + "Powinno mieć "
                        + Integer.toString(Authentication.MIN_PASSWORD_LENGTH) + "-"
                        + Integer.toString(Authentication.MAX_PASSWORD_LENGTH)
                        + " znaków";
                controllerManager.setMsg(msg, true);
                controllerManager.setController(
                        Id.MAIN_CONTROLLER.getIntRepresentation(), null);
                return new State1();
            }

            // zapisujemy tymczasowo hasło
            firstPassword = password;
            // ustawiamy prefix
            setPrefix("Powtórz hasło:");
            // zwracamy kolejny stan
            return new State3();
        }

        @Override
        public IState run(String param1, String[] parameters)
        {
            throw new RunMethodNotImplementedException();
        }
    }

    private class State3 implements IState
    {
        @Override
        public IState run(String param)
        {
            String password = param;

            if (!firstPassword.equals(password))
            {
                String msg = "Hasło nie zgadza się z poprzednio wprowadzonym";
                controllerManager.setMsg(msg, true);
                controllerManager.setController(
                        Id.MAIN_CONTROLLER.getIntRepresentation(), null);
                return new State1();
            }

            // blokujemy konsolę do czasu zakończenia działania przez register plugin
            setBlockConsole(true);
            // tworzymy skrót hasła
            password = Hash.createPasswordHash(password, login);
            // tworzymy tablicę parametrów
            String[] parameters = new String[]
            {
                login,
                password
            };
            // wyświetlamy wiadomość userowi
            String msg = "Próbuję zarejestrować";
            controllerManager.setMsg(msg, false);
            // uruchamiamy register plugin
            controllerManager.startPlugin(
                    MessageId.SIGN_UP.getIntRepresentation(), parameters);

            return new State1();
        }

        @Override
        public IState run(String param1, String[] parameters)
        {
            throw new RunMethodNotImplementedException();
        }
    }

    private interface IState
    {

        /**
         * Rzuca wyjątek RunMethodNotImplementedException jeżeli nie jest
         * zaimplementowana.
         *
         * @param param
         * @return
         */
        public IState run(String param);

        /**
         * Rzuca wyjątek RunMethodNotImplementedException jeżeli nie jest
         * zaimplementowana.
         *
         * @param param1
         * @param parameters
         * @return
         */
        public IState run(String param1, String[] parameters);
    }
}
