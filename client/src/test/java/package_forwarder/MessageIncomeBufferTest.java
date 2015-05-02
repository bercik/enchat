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

    public void testGet() throws Exception {
        MessageIncomeBuffer messageIncomeBuffer = new MessageIncomeBuffer();

        NetworkMessageIncome networkMessageIncome = new NetworkMessageIncome("Input", "signInput");

        //test funkcji append
        messageIncomeBuffer.append(networkMessageIncome);

        //test funkcji get, sprawdzimy czy to co wysłaliśmy zgadza się z tym co otrzymamy
        ArrayList<NetworkMessageIncome> networkMessageIncomeArrayList = messageIncomeBuffer.get();

        for(int i = 0; i < networkMessageIncomeArrayList.size(); ++i) {
            ArrayList<MessageSignPair> messageSignPairArrayList = networkMessageIncomeArrayList.get(i).getMessageSignPair();
            for(int k = 0; k < messageSignPairArrayList.size(); ++k)
                System.out.println("Message = " + messageSignPairArrayList.get(k).getMessage().toString() + " Sign = " + messageSignPairArrayList.get(k).getSign().toString());
        }
    }

    public void testIsException() throws Exception {
        MessageIncomeBuffer messageIncomeBuffer = new MessageIncomeBuffer();
        Exception ex = new IOException();
        messageIncomeBuffer.setException(ex);

        if(messageIncomeBuffer.isException() == true)
            System.out.println("Exception existed");
        else
            System.out.println("Null exception");
    }

    public void testIsAvailable() throws Exception {

    }

    public void testSetException() throws Exception {

    }

    public void testGetException() throws Exception {

    }
}