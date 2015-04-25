package messages.server_response;

/**
 * @author mateusz
 * @version 1.0
 */
public enum serverResponse {

    JUNK(0), INNCORRECT_PASSWORD(1), INNCORRECT_PASSWORD_LENGHT(2), INNCORRECT_LOGIN_LENGHT(3);


    serverResponse(int iid) {
        id = iid;
    }

    //zwraca wartość stanu
    public int getIntRepresentation() {
        return id;
    }

    private int id;
}
