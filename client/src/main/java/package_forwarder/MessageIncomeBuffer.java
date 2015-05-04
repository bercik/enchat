package package_forwarder;

import network.NetworkMessageIncome;

import java.util.ArrayList;
import java.util.List;

/**
 * Modified by robert at 4.05.2015.
 *
 * @author mateusz
 * @version 1.0
 */
public class MessageIncomeBuffer
{
    //funkcja zwraca listę z wiadomościami
    public synchronized List<NetworkMessageIncome> get()
    {
        // skopiuj listę
        List<NetworkMessageIncome> toReturn = new ArrayList<>(buffer);
        // wyczyść bufor
        buffer.clear();
        // zwróc listę
        return toReturn;
    }

    //funkcja zwraca true jeżeli jest jakiś wyjątek, jeżeli go nie ma
    //zwraca false
    public synchronized boolean isException()
    {
        return exception != null;
    }

    //funkcja dodaje wiadomość do listy z wiadomościami
    public synchronized void append(NetworkMessageIncome message)
    {
        buffer.add(message);
    }

    //jeżeli lista z wiadomościami jest pusta to zwracamy true
    public synchronized boolean isAvailable()
    {
        return !buffer.isEmpty();
    }

    //funkcja ustawia wyjątek
    public synchronized void setException(Exception eexception)
    {
        exception = new Exception(eexception);
    }

    //funkcja zwraca wyjątek
    public synchronized Exception getException()
    {
        Exception toReturn = new Exception(exception);
        exception = null;
        return toReturn;
    }

    private final ArrayList<NetworkMessageIncome> buffer = new ArrayList<>();
    private Exception exception = null;
}
