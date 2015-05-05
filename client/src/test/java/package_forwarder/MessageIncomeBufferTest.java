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

    public void testList() throws Exception {
        MessageIncomeBuffer messageIncomeBuffer = new MessageIncomeBuffer();

        NetworkMessageIncome networkMessageIncome = new NetworkMessageIncome("Input", "signInput");

        //test funkcji isAvailable gdy lista jest pusta
        if(messageIncomeBuffer.isAvailable() == true)
            System.out.println("List has element");
        else
            System.out.println("List is empty");

        //test funkcji append
        messageIncomeBuffer.append(networkMessageIncome);

        //gdy dodamy element do listy
        if(messageIncomeBuffer.isAvailable() == true)
            System.out.println("List has element");
        else
            System.out.println("List is empty");

        //test funkcji get, sprawdzimy czy to co wysłaliśmy zgadza się z tym co otrzymamy
        ArrayList<NetworkMessageIncome> networkMessageIncomeArrayList = (ArrayList<NetworkMessageIncome>) messageIncomeBuffer.get();

        for(int i = 0; i < networkMessageIncomeArrayList.size(); ++i) {
            ArrayList<MessageSignPair> messageSignPairArrayList = (ArrayList<MessageSignPair>) networkMessageIncomeArrayList.get(i).getMessageSignPair();
            for(int k = 0; k < messageSignPairArrayList.size(); ++k)
                System.out.println("Message = " + new String(messageSignPairArrayList.get(k).getMessage()) + " Sign = " + new String(messageSignPairArrayList.get(k).getSign()) );
        }
    }

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