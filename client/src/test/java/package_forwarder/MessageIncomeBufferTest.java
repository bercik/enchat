package package_forwarder;

import junit.framework.TestCase;
import network.MessageSignPair;
import network.NetworkMessageIncome;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mateusz on 02.05.15.
 */
public class MessageIncomeBufferTest extends TestCase {
    public void testException() throws Exception {
        MessageIncomeBuffer messageIncomeBuffer = new MessageIncomeBuffer();
        Exception ex = new IOException();
        messageIncomeBuffer.setException(ex);

        if (messageIncomeBuffer.isException() == true){
            System.out.println("Exception existed");

            //test funkcji getException oraz wypisanie widomości wyjątku
            Exception exception = messageIncomeBuffer.getException();
            System.out.println(exception.getMessage());

        }
        else
            System.out.println("Null exception");
    }
}