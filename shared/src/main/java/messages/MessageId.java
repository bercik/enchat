package messages;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * Created by tochur on 17.04.15. Modified by robert on 29.04.15.
 * Official version verified by (tochur) on 30.04.15
 */
public enum MessageId
{
    // śmieci (używane do sprawdzenia czy połączenie istnieje)
    JUNK(0),
    // logowanie
    // BAD_LOGIN_OR_PASSWORD - zły login lub hasło
    // TOO_MUCH_USERS_LOGGED - za dużo zalogowanych użytkowników
    // ErrorId.ALREADY_LOGGED - nie moża być zalogowanym przy pomocy jednego loginu kilka razy, następuje wylogowanie z poprzedniej sesji.
    LOG_IN(1, ErrorId.BAD_LOGIN_OR_PASSWORD, ErrorId.TOO_MUCH_USERS_LOGGED, 
            ErrorId.ALREADY_LOGGED),
    // rejestracja
    // BUSY_LOGIN - login zajęty
    // INCORRECT_LOGIN - nieprawidłowy login 
    // (zawiera inne znaki niż alfanumeryczne ASCII)
    SIGN_UP(2, ErrorId.BUSY_LOGIN, ErrorId.INCORRECT_LOGIN, 
            ErrorId.BAD_PASSWORD_LENGTH, ErrorId.TO_MUCH_REGISTERED),
    // żądanie konwersacji (klient -> serwer)
    // USER_NOT_LOGGED - użytkownik z którym chcemy rozmawiać jest niezalogowany
    // BUSY_USER - użytkownik z którym chcemy rozmawiać jest zajęty
    // (prowadzi rozmowę albo wpisał nas na czarną listę)
    // CONVERSATION_WITH_ANOTHER_USER - prowadzimy aktualnie rozmowę z kimś innym
    // ON_BLACK_LIST - użytkownik jest na naszej czarnej liście
    CONVERSATION_REQUEST(3, ErrorId.USER_NOT_LOGGED, ErrorId.BUSY_USER, 
            ErrorId.CONVERSATION_WITH_ANOTHER_USER, ErrorId.ON_BLACK_LIST),
    // przychodzące połączenie (serwer -> klient)
    // IM_BUSY - ktoś próbował się z nami połączyć, ale rozmawiamy z kimś innym
    INCOMING_CONVERSATION(4, ErrorId.IM_BUSY),
    // wiadomość od klienta (klient -> serwer)
    // FAILED - nie udało wysłać się wiadomości (jakikolwiek powód)
    CLIENT_MESSAGE(5, ErrorId.FAILED),
    // wiadomość od serwera (serwer -> klient)
    SERVER_MESSAGE(6),
    // zakończenie rozmowy
    CONVERSATIONALIST_DISCONNECTED(7),
    // prośba o listę zalogowanych użytkowników
    CLIENTS_LIST(8),
    // prośba o moją czarną listę
    BLACK_LIST(9),
    // dodanie użytkownika do czarnej listy
    // USER_DOESNT_EXIST - użytkownik nie istnieje
    // TOO_MUCH_USERS_ON_BLACKLIST - posiadamy za dużo osób na czarnej liście
    ADD_TO_BLACK_LIST(10, ErrorId.USER_DOESNT_EXIST, 
            ErrorId.TOO_MUCH_USERS_ON_BLACKLIST, ErrorId.ALREADY_ADDED),
    // usuń użytkownika z czarnej listy
    // USER_NOT_ON_BLACKLIST - użytkownik nie znajduje się na czarnej liście
    REMOVE_FROM_BLACK_LIST(11, ErrorId.USER_NOT_ON_BLACKLIST, ErrorId.USER_NOT_EXIST),
    // rozłącz z serwerem ( // TODELETE - niepotrzebne, do usunięcia )
    DISCONNECT(12),
    // wyloguj z serwera użytkownika
    LOGOUT(13),
    // błąd serwera
    // MESSAGE_DECRYPTING_FAILED - nie udało się odszyfrować wiadomości
    SERVER_ERROR(14, ErrorId.MESSAGE_DECRYPTING_FAILED, 
            ErrorId.MESSAGE_ENCRYPTING_FAILED, ErrorId.SERVER_OVERLOADED);

    private final int id;
    private final EnumSet<ErrorId> errorIds;

    private MessageId(int id, ErrorId... eerrorIds){
        this.id = id;

        errorIds = EnumSet.noneOf(ErrorId.class); // make an empty enumset
        errorIds.add(ErrorId.OK); // always contains OK error
        errorIds.addAll(Arrays.asList(eerrorIds)); // add varargs to it
    }

    public static enum ErrorId{
        OK(0),
        BAD_LOGIN_OR_PASSWORD(1),
        TOO_MUCH_USERS_LOGGED(2),
        ALREADY_LOGGED(3),
        BUSY_LOGIN(1),
        INCORRECT_LOGIN(2),
        BAD_PASSWORD_LENGTH(3),
        TO_MUCH_REGISTERED(4),
        USER_NOT_LOGGED(1),
        BUSY_USER(2),
        CONVERSATION_WITH_ANOTHER_USER(3),
        ON_BLACK_LIST(4),
        IM_BUSY(1),
        FAILED(1),
        USER_DOESNT_EXIST(1),
        TOO_MUCH_USERS_ON_BLACKLIST(2),
        ALREADY_ADDED(3),
        USER_NOT_ON_BLACKLIST(1),
        USER_NOT_EXIST(2),
        MESSAGE_DECRYPTING_FAILED(1),
        MESSAGE_ENCRYPTING_FAILED(2),
        SERVER_OVERLOADED(3);

        private final int id;

        private ErrorId(int iid)
        {
            id = iid;
        }

        public int getIntRepresentation()
        {
            return id;
        }
    }

    public ErrorId createErrorId(int id) {
        int max = Integer.MIN_VALUE;

        for (ErrorId errorId : errorIds){
            if (errorId.getIntRepresentation() > max){
                max = errorId.getIntRepresentation();
            }

            if (errorId.getIntRepresentation() == id){
                return errorId;
            }
        }
        throw new IncorrectErrorId("Incorrect error id for "
                + getClass().getSimpleName() + " !!!\nWas: " + id
                + " expected: [0 - " + Integer.toString(max) + "]");
    }

    public static MessageId createMessageId(int id) {
        for (MessageId messageId : MessageId.values()){
            if (messageId.getIntRepresentation() == id){
                return messageId;
            }
        }
        throw new IncorrectMessageId("Incorrect message id !!! Was: " + id
                + " expected: [0 - 13]");
    }

    public int getIntRepresentation()
    {
        return id;
    }
}
