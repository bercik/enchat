package controller.responders.exceptions;

/**
 * When user send message that is not allowed in his stare.
 * ex. he want to logout, but he he is not logged.
 * @author Created by tochur on 24.04.15.
 */
public class IncorrectUserStateException extends RuntimeException {
}
