package controller.responders;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import model.containers.temporary.UserStates;
import model.user.UserState;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;
import server.listeners.message.InputStreamsHandler;
import server.sender.OutStreams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tochur on 13.05.15.
 *
 * This class is responsible for actualization state of users that can exchange information with server
 *
 * !!!!!!!!!!!!! MODIFICATION NEEDED !!!!!!!!!!!! - add  model state change.
 */
public class NewClientHandler implements Runnable {
    private Socket clientSocket;
    private PublicKey publicKey;
    private PublicKeysManager publicKeysManager;
    private OutStreams outStreams;
    private UserStates userStates;
    private static Integer ID = 0;

    private InputStreamsHandler inputStreamsListener;

    @Inject
    public NewClientHandler(InputStreamsHandler inputStreamsListener,@Named("Server") PublicKey publicKey, PublicKeysManager publicKeysManager, OutStreams outStreams, UserStates userStates){
        this.inputStreamsListener = inputStreamsListener;
        this.publicKey = publicKey;
        this.publicKeysManager = publicKeysManager;
        this.outStreams = outStreams;
        this.userStates = userStates;
    }

    public void setParameters(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try{
            DataInputStream inputStream = new DataInputStream( clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream( clientSocket.getOutputStream());

            inputStreamsListener.addStreamToScan(ID, inputStream);

            //Wysłanie klucza publicznego
            PublicKeyInfo publicKeyInfo = new PublicKeyInfo(publicKey);
            publicKeyInfo.send(outputStream);

            PublicKeyInfo clientPublicKeyInfo = new PublicKeyInfo(inputStream);
            BigInteger modulus =  clientPublicKeyInfo.getModulus();
            BigInteger exponent = clientPublicKeyInfo.getExponent();

            PublicKey publicKey = clientPublicKeyInfo.getPublicKey();

            publicKeysManager.addKey(ID, publicKey, modulus, exponent);
            outStreams.addStream(ID, outputStream);
            userStates.updateState(ID, UserState.CONNECTED_TO_SERVER);

            ID++;
        } catch (IOException e) {
            System.out.println("Enable to open streams on new client socket. Connection rejected.");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (GeneratingPublicKeyException e) {
            e.printStackTrace();
        }

    }
}
