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

/**
 *
 * @author robert
 */
public class LoginController extends CommandLineController
{
    private String login;

    @Override
    public void putSpecialKey(Key key)
    {
        // wywołujemy metodę klasy bazowej
        super.putSpecialKey(key);
        // jeżeli escape i konsola nie jest zablokowana
        // to wracamy do main controller
        if (!getBlockConsole() && key == Key.ESC)
        {
            controllerManager.setController(
                Id.MAIN_CONTROLLER.getIntRepresentation(), null);
            controllerManager.setMsg("", true);
        }
    }

    @Override
    protected void tabPressed()
    {
        // do nothing
    }
    
    @Override
    protected void route(String input)
    {
        String password = input;
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
            return;
        }

        // blokujemy konsolę do czasu zakończenia działania przez login plugin
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
        String msg = "Próbuję zalogować jako " + login;
        controllerManager.setMsg(msg, false);
        // uruchamiamy login plugin
        controllerManager.startPlugin(
            MessageId.LOG_IN.getIntRepresentation(), parameters);
    }

    @Override
    public void start(String previousCommand, String[] parameters)
    {
        // sprawdzamy czy login został wpisany
        if (parameters.length < 1)
        {
            controllerManager.setMsg(StringFormatter.badCommand("login"), true);
            controllerManager.setController(
                    Id.MAIN_CONTROLLER.getIntRepresentation(), null);
            return;
        }
        // sprawdzamy poprawność loginu
        if (!Authentication.isLoginCorrect(parameters[0]))
        {
            String msg = "Nieprawidłowy login " + parameters[0]
                    + ". Prawidłowy ma "
                    + Integer.toString(Authentication.MIN_LOGIN_LENGTH) + "-"
                    + Integer.toString(Authentication.MAX_LOGIN_LENGTH)
                    + " znaków i składa się z samych liter, cyfr lub"
                    + " znaków podkreślenia";
            controllerManager.setMsg(msg, true);
            controllerManager.setController(
                    Id.MAIN_CONTROLLER.getIntRepresentation(), null);
            return;
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
    }

    @Override
    public void updateError(int error)
    {
        if (error != MessageId.ErrorId.OK.getIntRepresentation())
        {
            // zmieniamy na MainController
            controllerManager.setController(
                    Id.MAIN_CONTROLLER.getIntRepresentation(), null);
        }

        setBlockConsole(false);
    }
}
