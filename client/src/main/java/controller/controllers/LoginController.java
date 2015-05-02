/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllers;

import app_info.Id;
import messages.MessageId;
import util.Authentication;

/**
 *
 * @author robert
 */
public class LoginController extends CommandLineController
{
    private String login;
    
    @Override
    protected void route(String input)
    {
        // TODO
        
        String password = input;
        // sprawdzamy poprawność hasła
        if (!Authentication.isPasswordCorrect(password))
        {
            String msg = "Zbyt krótkie lub zbyt długie hasło. "
                    + "Powinno mieć " +
                    Integer.toString(Authentication.MIN_PASSWORD_LENGTH) + "-" +
                    Integer.toString(Authentication.MAX_PASSWORD_LENGTH) +
                    " znaków";
            controllerManager.setMsg(msg, true);
            controllerManager.setController(
                    Id.MAIN_CONTROLLER.getIntRepresentation(), null);
            return;
        }

        // blokujemy konsolę do czasu zakończenia działania przez login plugin
        setBlockConsole(true);
        // TODO
        // uruchamiamy login plugin
    }

    @Override
    public void start(String previousCommand, String[] parameters)
    {
        // sprawdzamy czy login został wpisany
        if (parameters.length < 1)
        {
            String msg = "Złe wywołanie komendy. Prawidłowo: /login username";
            controllerManager.setMsg(msg, true);
            controllerManager.setController(
                    Id.MAIN_CONTROLLER.getIntRepresentation(), null);
            return;
        }
        // sprawdzamy poprawność loginu
        if (!Authentication.isLoginCorrect(parameters[0]))
        {
            String msg = "Nieprawidłowy login " + parameters[0] + 
                    ". Prawidłowy ma " +
                    Integer.toString(Authentication.MIN_LOGIN_LENGTH) + "-" +
                    Integer.toString(Authentication.MAX_LOGIN_LENGTH) +
                    " znaków i składa się z samych liter, cyfr lub"
                    + " znaku podkreślenia";
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
    }

    @Override
    public void updateError(int error)
    {
        // wyświetlamy komunikat o błędzie
        MessageId.ErrorId errorId = MessageId.LOG_IN.createErrorId(error);
        String msg = "Nieudane logowanie: " + errorId.toString();
        controllerManager.setMsg(msg, true);
        // zmieniamy na MainController
        controllerManager.setController(
                Id.MAIN_CONTROLLER.getIntRepresentation(), null);
    }
}
