/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.Id;
import java.util.Arrays;
import java.util.List;
import messages.MessageId;
import util.Authentication;
import util.IState;
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
        // wyświetlamy komunikat o błędzie
        MessageId.ErrorId errorId = MessageId.LOG_IN.createErrorId(error);
        String msg = "Nieudana rejestracja: " + errorId.toString();
        controllerManager.setMsg(msg, true);
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
                String msg = "Złe wywołanie komendy. Prawidłowo:"
                        + " /register username";
                controllerManager.setMsg(msg, true);
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
            // zwracamy kolejny stan
            return new State2();
        }

        @Override
        public IState run(String[] parameters)
        {
            throw new RunMethodNotImplementedException();
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
        public IState run(String[] parameters)
        {
            throw new RunMethodNotImplementedException();
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
            // TODO

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
            // TODO
            // uruchamiamy register plugin
            
            return new State1();
        }

        @Override
        public IState run(String[] parameters)
        {
            throw new RunMethodNotImplementedException();
        }

        @Override
        public IState run(String param1, String[] parameters)
        {
            throw new RunMethodNotImplementedException();
        }
    }
}
