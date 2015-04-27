package app_info;

/**
 * @author mateusz
 * @version 1.0
 */

//klasa służaca do sprawdzania czy stan w jakim ma znajdować się połączenie jest poprawne
public class IncorrectState extends Exception {
    IncorrectState(String message) {
        super(message);
    }
}
