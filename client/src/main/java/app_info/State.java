package app_info;

/**
 * @author mateusz
 * @version 1.0
 */
public enum State {
    NOT_CONNECTED(0),
    CONNECTED(1),
    LOGGED(2),
    CONVERSATION(3);

    State(int vvalue) {
        value = vvalue;
    }

    private int value;
}
