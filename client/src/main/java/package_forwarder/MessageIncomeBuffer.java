package package_forwarder;

import network.NetworkMessageIncome;

import java.util.ArrayList;

/**
 * @author mateusz
 * @version 1.0
 */
public class MessageIncomeBuffer {

    //funkcja zwraca listę z wiadomościami
    public synchronized ArrayList<NetworkMessageIncome> get() {
        return new ArrayList<>(buffer);
    }

    //funkcja zwraca true jeżeli jest jakiś wyjątek, jeżeli go nie ma
    //zwraca false
    public synchronized boolean isException() {
        if(exception == null)
            return false;
        else
            return true;
    }

    //funkcja dodaje wiadomość do listy z wiadomościami
    public synchronized void append(NetworkMessageIncome message) {
        buffer.add(message);
    }

    //jeżeli lista z wiadomościami jest pusta to zwracamy true
    public synchronized boolean isAvailable() {
        if(buffer.isEmpty() == true)
            return false;
        else
            return true;
    }

    //funkcja ustawia wyjątek
    public synchronized void setException(Exception eexception) {
        exception = new Exception(eexception);
    }

    //funkcja zwraca wyjątek
    public synchronized Exception getException() {
        return new Exception(exception);
    }

    private ArrayList<NetworkMessageIncome> buffer = new ArrayList<>();
    private Exception exception = null;
}
