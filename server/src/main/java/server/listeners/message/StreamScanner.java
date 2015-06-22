package server.listeners.message;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * This class is used to scan for incoming data from input stream
 *
 * @author Created by tochur on 14.05.15.
 */
public class StreamScanner {

    /**
     * Checks weather there is some data available in the buffer
     * @param in DataInputStream, stream to check
     * @return boolean, true when is not empty, false, otherwise.
     * @throws IOException when there was an exception during reading.
     */
    public boolean canRead(DataInputStream in) throws IOException {
        if (isEmpty(in))
            return false;
        return true;
    }

    /**
     * Checks weather there is some data available in the buffer
     * @param inputStream DataInputStream, stream to check
     * @return boolean, true when is empty, false, otherwise.
     * @throws IOException when there was an exception during reading.
     */
    public boolean isEmpty(DataInputStream inputStream) throws IOException {
        return inputStream.available() <= 0;
    }
}
