package package_forwarder;

import network.Connection;
import network.MessageSignPair;
import network.NetworkMessageIncome;
import network.NetworkMessageOutcome;
import rsa.RSA;
import rsa.exceptions.GeneratingPublicKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import network.SendException;

/**
 * @author mateusz
 * @version 1.0
 */
public class PackageForwarder implements Runnable
{
    //Konstruktor służacy do zapisywania referencji do obiektu typu MessageIncomeBuffer
    public PackageForwarder(MessageIncomeBuffer mmessageIncomeBuffer)
    {
        messageIncomeBuffer = mmessageIncomeBuffer;
    }

    @Override
    public void run()
    {
        // pętla nieskończona która będzie nasłuchiwała strumień
        // dopóki nie nastąpi przerwanie
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                if (conn.isEmpty() == false)
                {
                    // won't compile
                    //NetworkMessageIncome networkMessageIncome = new NetworkMessageIncome();
                    // add by robert to compile
                    NetworkMessageIncome networkMessageIncome
                            = new NetworkMessageIncome();
                    networkMessageIncome.recv(conn);
                    messageIncomeBuffer.append(networkMessageIncome);
                }
            }
            catch (Exception e)
            {
                if (!Thread.currentThread().isInterrupted())
                {
                    this.disconnect();
                    messageIncomeBuffer.setException(e);
                }
                
                return;
            }
        }
    }

    public void connect() throws IOException, NoSuchAlgorithmException,
            InvalidKeyException, GeneratingPublicKeyException,
            NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException, InvalidKeySpecException,
            ClassNotFoundException
    {
        conn = new Connection();
        if (thread == null)
        {
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void send(int id, String[] parameters) throws SendException
    {
        try
        {
            byte[] message;
            byte[] signMessage;

            ArrayList<MessageSignPair> messageSignPairs = new ArrayList<>();

            for (int i = 0; i < parameters.length; ++i)
            {
                message = parameters[i].getBytes();
                signMessage = RSA.sign(message, conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());
                messageSignPairs.add(new MessageSignPair(message, signMessage));
            }

            NetworkMessageOutcome networkMessageOutcome = new NetworkMessageOutcome(id, messageSignPairs);
            networkMessageOutcome.send(conn);
        }
        catch (Exception ex)
        {
            disconnect();
            throw new SendException("An error occur when try to send data", ex);
        }
    }

    public void disconnect()
    {
        thread.interrupt();
        conn.close();
    }

    private Connection conn;
    private Thread thread;
    private final MessageIncomeBuffer messageIncomeBuffer;
}
