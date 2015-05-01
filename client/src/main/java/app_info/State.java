package app_info;

import app_info.exceptions.IncorrectStateException;

/**
 * @author mateusz
 * @version 1.0
 */
public enum State
{
    NOT_CONNECTED(0),
    CONNECTED(1),
    LOGGED(2),
    CONVERSATION(3);

    public static final State[] ALL = new State[]
    {
        NOT_CONNECTED, CONNECTED, LOGGED, CONVERSATION
    };

    State(int iid)
    {
        id = iid;
    }

    //funkcja służąca do tworzenia stanu
    public static State createState(int id) throws IncorrectStateException
    {
        for (State state : State.values())
        {
            if (state.getID() == id)
            {
                return state;
            }
        }
        throw new IncorrectStateException("Incorrect state !!! Was " + id + " expected: [0 - 3]");
    }

    public int getID()
    {
        return id;
    }

    private int id;
}
