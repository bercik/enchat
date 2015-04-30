package app_info.exceptions;

/**
 * @author mateusz
 * @version 1.0
 */

//klasa służaca do sprawdzania czy stan w jakim ma znajdować się połączenie jest poprawne
public class IncorrectStateException extends Exception {
    public IncorrectStateException(String message) {
        super(message);
    }
}
