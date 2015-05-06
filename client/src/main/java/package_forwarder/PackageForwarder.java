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

/**
 * @author mateusz
 * @version 1.0
 */
public class PackageForwarder implements Runnable{

    //Konstruktor służacy do zapisywania referencji do obiektu typu MessageIncomeBuffer
    public PackageForwarder(MessageIncomeBuffer mmessageIncomeBuffer) {
        messageIncomeBuffer = mmessageIncomeBuffer;
    }

    public void run() {

        //pętla nieskończona która będzie nasłuchiwała strumień
        while(true) {
            try {
                if(conn.isEmpty() == false) {
                    // won't compile
                    //NetworkMessageIncome networkMessageIncome = new NetworkMessageIncome();
                    // add by robert to compile
                    NetworkMessageIncome networkMessageIncome
                            = new NetworkMessageIncome(null, null);
                    networkMessageIncome.recv(conn);
                    messageIncomeBuffer.append(networkMessageIncome);
                }

            } catch (IOException e) {
                messageIncomeBuffer.setException(e);
            } catch (NoSuchPaddingException e) {
                messageIncomeBuffer.setException(e);
            } catch (NoSuchAlgorithmException e) {
                messageIncomeBuffer.setException(e);
            } catch (IllegalBlockSizeException e) {
                messageIncomeBuffer.setException(e);
            } catch (BadPaddingException e) {
                messageIncomeBuffer.setException(e);
            } catch (SignatureException e) {
                messageIncomeBuffer.setException(e);
            } catch (InvalidKeyException e) {
                messageIncomeBuffer.setException(e);
            } catch (InvalidKeySpecException e) {
                messageIncomeBuffer.setException(e);
            }

        }

    }

    public void connect() throws IOException, NoSuchAlgorithmException, InvalidKeyException, GeneratingPublicKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, ClassNotFoundException {
        conn = new Connection();
        if(thread == null) {
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void send(int id, String[] parameters) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] message = null;
        byte[] signMessage = null;

        ArrayList<MessageSignPair> messageSignPairs = new ArrayList<>();

        for(int i = 0; i < parameters.length; ++i) {
            message = parameters[i].getBytes();
            signMessage = RSA.sign(message, conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());
            messageSignPairs.add(new MessageSignPair(message, signMessage));
        }

        NetworkMessageOutcome networkMessageOutcome = new NetworkMessageOutcome(id, messageSignPairs);
        networkMessageOutcome.send(conn);
    }

    private Connection conn;
    private Thread thread;
    private MessageIncomeBuffer messageIncomeBuffer;
}
