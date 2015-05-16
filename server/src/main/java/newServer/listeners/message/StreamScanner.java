package newServer.listeners.message;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by tochur on 14.05.15.
 */
public class StreamScanner {

    public boolean canRead(DataInputStream in) throws IOException {
        if (isEmpty(in))
            return false;
        return true;
    }

    public boolean isEmpty(DataInputStream inputStream) throws IOException {
        return inputStream.available() <= 0;
    }
}
