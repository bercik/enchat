package controller.responders;

import com.google.inject.Inject;
import com.google.inject.name.Named;
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
 */
public class NewClientHandler implements Runnable {
    private Socket clientSocket;
    private PublicKeysManager publicKeysManager;
    private OutStreams outStreams;
    private UserStates userStates;
    private static Integer ID = 0;

    private InputStreamsHandler inputStreamshandler;

    @Inject
    public NewClientHandler(InputStreamsHandler inputStreamshandler,@Named("Server") PublicKey publicKey, PublicKeysManager publicKeysManager, OutStreams outStreams, UserStates userStates){
        this.inputStreamshandler = inputStreamshandler;
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
            System.out.println("Connecting new client with id: " + ID);
            int newClientID = ID++;

            //Creating streams
            setUpStreams();

            //Wysłanie klucza publicznego
            exchangePublicKeys();

            //Updating model
            updateModel(newClientID);
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

    protected void exchangePublicKeys() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, GeneratingPublicKeyException {
        PublicKeyInfo publicKeyInfo = new PublicKeyInfo(publicKey);
        publicKeyInfo.send(outputStream);

        PublicKeyInfo clientPublicKeyInfo = new PublicKeyInfo(inputStream);
        modulus =  clientPublicKeyInfo.getModulus();
        exponent = clientPublicKeyInfo.getExponent();

        publicKey = clientPublicKeyInfo.getPublicKey();
    }

    protected void setUpStreams() throws IOException {
        inputStream = new DataInputStream( clientSocket.getInputStream());
        outputStream = new DataOutputStream( clientSocket.getOutputStream());
    }

    protected void updateModel(int newID){
        inputStreamshandler.addStreamToScan(newID, inputStream);
        publicKeysManager.addKey(newID, publicKey, modulus, exponent);
        outStreams.addStream(newID, outputStream);
        userStates.updateState(newID, UserState.CONNECTED_TO_SERVER);
    }

    private BigInteger modulus;
    private BigInteger exponent;
    private PublicKey publicKey;
    DataInputStream inputStream;
    DataOutputStream outputStream;
}
