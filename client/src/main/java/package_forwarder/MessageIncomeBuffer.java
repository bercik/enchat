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
        return true;
    }

    public synchronized void append(NetworkMessageIncome message) {
        buffer.add(message);
    }
    private ArrayList<NetworkMessageIncome> buffer = new ArrayList<>();
}
