package messages.server_request;

/**
 * Enum-y reprezentują przeważnie daną komendę,
 * jedynie DISCONNECT nie ma odpowiednika w komendzie
 * wpisanywanej w linii poleceń.
 * @author mateusz
 * @version 1.0
 */
public enum serverRequest {
    
    JUNK(0),
    CONNECT(1),
    DISCONNECT(2),
    REGISTER(3),
    LOGIN(4),
    LOGOUT(5),
    USERS(6),
    TALK(7),
    ENDTALK(8),
    BLACKLIST(9),
    BLOCK(10),
    UNBLOCK(11);

    serverRequest(int iid) {
        id = iid;
    }

    //zwraca wartość stanu
    public int getIntRepresentation() {
        return id;
    }

    private int id;
}
