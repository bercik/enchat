package messages.server_response;

/**
 * Wyjaśnienie enum-ów:
 * <ul>
 * 
 * <li>MAX_REGISTERED_USERS
 *      - serwer posiada zbyt dużą liczbę zapisanych użytkowników w swojej bazie danych,
 *      odpowiedź dla zapytania REGISTER</li>
 * 
 * <li>USERNAME_IS_USED
 *      - podana nazwa użytkownika jest już przez kogoś używana,
 *      odpowiedź dla zapytania REGISTER</li>
 * 
 * <li>SERVER_OVERLOADED
 *      - serwer jest przeciążony bo maksymalna ilość aktywnych połączeń została osiągnięta,
 *      odpowiedź dla zapytania CONNECT</li>
 * 
 * <li>LOGIN_OR_PASS_INVALID
 *      - login lub hasło jest błędne (albo login nie istnieje albo hasło jest błędne),
 *      odpowiedź dla zapytania LOGIN</li>
 * 
 * <li>USER_IS_ENGAGED
 *      - drugi użytkownik już z kimś rozmawia,
 *      odpowiedź dla zapytania TALK</li>
 * 
 * <li>USER_IS_NOT_LOGGED
 *      - drugi użytkownik nie jest zalogowany,
 *      odpowiedź dla zapytania TALK</li>
 * 
 * <li>USER_NOT_EXISTS
 *      - drugi użytkownik o określonej nazwie nie istnieje,
 *      odpowiedź dla zapytania TALK, BLOCK, UNBLOCK</li>
 * 
 * <li>LACK_OF_ACTIVE_USERS
 *      - nie ma zalogowanych innych użytkowników,
 *      odpowiedź dla zapytania USERS</li>
 * 
 * <li>EMPTY_BLACKLIST
 *      - czarna lista jest pusta,
 *      odpowiedź dla zapytania BLACKLIST</li>
 * 
 * <li>BLACKLIST_OVERLOADED
 *      - maksymalna liczba osób na czarnej liście została osiągnięta,
 *      odpowiedź dla zapytania BLOCK</li>
 * 
 * <li>USER_IS_ALREADY_IN_BLACKLIST
 *      - drugi użytkownik jest już na czarnej liście,
 *      odpowiedź dla zapytania BLOCK</li>
 * 
 * <li>USER_IS_NOT_IN_BLACKLIST
 *      - drugi użytkownik nie jest na czarnej liście,
 *      odpowiedź dla zapytania UNBLOCK</li>
 * 
 * </ul>
 * @author mateusz
 * @version 1.0
 */
public enum serverResponse {

    JUNK(0),
    MAX_REGISTERED_USERS(1),
    USERNAME_IS_USED(2),
    SERVER_OVERLOADED(3),
    LOGIN_OR_PASS_INVALID(4),
    USER_IS_ENGAGED(5),
    USER_IS_NOT_LOGGED(6),
    USER_NOT_EXISTS(7),
    LACK_OF_ACTIVE_USERS(8),
    EMPTY_BLACKLIST(9),
    BLACKLIST_OVERLOADED(10),
    USER_IS_ALREADY_IN_BLACKLIST(11),
    USER_IS_NOT_IN_BLACKLIST(12);

    serverResponse(int iid) {
        id = iid;
    }

    //zwraca wartość stanu
    public int getIntRepresentation() {
        return id;
    }

    private int id;
}
