package messages.server_request;

/**
 * @author mateusz
 * @version 1.0
 */
public enum serverRequest {
    JUNK(0), SERVER_OVERLOAD(1), USER_EXIST(2);

    serverRequest(int iid) {
        id = iid;
    }

    //zwraca wartość stanu
    public int getIntRepresentation() {
        return id;
    }

    private int id;
}
