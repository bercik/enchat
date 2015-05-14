package model;

import java.io.DataInputStream;
import java.net.Socket;

/**
 * Created by tochur on 13.05.15.
 *
 * Represents the Stream, from with server receive data from user.
 */
public class ClientInput {
    private final DataInputStream inputStream;
    private final Integer ID;

    public ClientInput(DataInputStream inputStream, Integer ID){
        this.inputStream = inputStream;
        this.ID = ID;
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public Integer getID() {
        return ID;
    }
}
