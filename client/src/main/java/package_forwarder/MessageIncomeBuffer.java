package package_forwarder;

import network.NetworkMessageIncome;

import java.util.ArrayList;

/**
 * @author mateusz
 * @version 1.0
 */
public class MessageIncomeBuffer {

    public synchronized ArrayList<NetworkMessageIncome> get() {
        return new ArrayList<>(buffer);
    }

    public synchronized boolean isException() {
        if(exception == null)
            return false;
        else
            return true;
    }

    public synchronized void append(NetworkMessageIncome message) {
        buffer.add(message);
    }

    //jeżeli lista z wiadomościami jest pusta to zwracamy true
    public synchronized boolean isAvailable() {
        if(buffer.isEmpty() == true)
            return true;
        else
            return false;
    }

    public synchronized void setException(Exception eexception) {
        exception = new Exception(eexception.getMessage());
    }

    public synchronized Exception getException() {
        return new Exception(exception.getMessage());
    }

    private ArrayList<NetworkMessageIncome> buffer = new ArrayList<>();
    private Exception exception = null;
}
