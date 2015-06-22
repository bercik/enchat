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
 * This class is responsible for keyExchange and actualization state of users that can exchange information with server
 *
 * @author Created by tochur on 13.05.15.
 *
 */
public class NewClientHandler implements Runnable {
    private Socket clientSocket;
    private PublicKeysManager publicKeysManager;
    private OutStreams outStreams;
    private UserStates userStates;
    //Unique user identifier - per session.
    private static Integer ID = 0;

    private InputStreamsHandler inputStreamsHandler;

    /**
     * Creates new ClientHandler, util that
     * @param inputStreamsHandler InputStreamsHandler, util that manages the stream, used to add new input stream, associated with connected user.
     * @param publicKey PublicKey, publicKey of the server - to exchange.
     * @param publicKeysManager PublicKeysManager, utils to PublicKey management, used to add new user and associated with hin PublicKey
     * @param outStreams OutStreams, util that manages the Output streams, used to add new output stream, associated with connected user.
     * @param userStates UserStates, util used to manage users states.
     */
    @Inject
    public NewClientHandler(InputStreamsHandler inputStreamsHandler,@Named("Server") PublicKey publicKey, PublicKeysManager publicKeysManager, OutStreams outStreams, UserStates userStates){
        this.inputStreamsHandler = inputStreamsHandler;
        this.publicKey = publicKey;
        this.publicKeysManager = publicKeysManager;
        this.outStreams = outStreams;
        this.userStates = userStates;
    }

    public void setParameters(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    /**
     * Connection establishing with new user.
     */
    @Override
    public void run() {
        try{
            int newClientID = ID++;

            //Creating streams
            setUpStreams();

            //Exchanging Keys
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

    /**
     * Exchanges PublicKeys using Util classes from shared module and updates model (publicKeyContainer).
     * @throws IOException when IOException occur.
     * @throws InvalidKeySpecException when KeySpecification is not valid.
     * @throws NoSuchAlgorithmException when programmer passer as String parameter wrong algorithm (ex "RZA", despite "RSA")
     * @throws GeneratingPublicKeyException when system was not able to generate PublicKey based on info from stream.
     */
    protected void exchangePublicKeys() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, GeneratingPublicKeyException {
        PublicKeyInfo publicKeyInfo = new PublicKeyInfo(publicKey);
        publicKeyInfo.send(outputStream);

        PublicKeyInfo clientPublicKeyInfo = new PublicKeyInfo(inputStream);
        modulus =  clientPublicKeyInfo.getModulus();
        exponent = clientPublicKeyInfo.getExponent();

        publicKey = clientPublicKeyInfo.getPublicKey();
    }

    /**
     * Opening the streams on clientSocket.
     * @throws IOException when an exception occurred, and streams were not able to be created.
     */
    protected void setUpStreams() throws IOException {
        inputStream = new DataInputStream( clientSocket.getInputStream());
        outputStream = new DataOutputStream( clientSocket.getOutputStream());
    }

    /**
     * Updates state of the user with specified id.
     * @param newID Integer, id of the user who will be added to model.
     */
    protected void updateModel(int newID){
        inputStreamsHandler.addStreamToScan(newID, inputStream);
        publicKeysManager.addKey(newID, publicKey, modulus, exponent);
        outStreams.addStream(newID, outputStream);
        userStates.updateState(newID, UserState.CONNECTED_TO_SERVER);
    }

    private BigInteger modulus;
    private BigInteger exponent;
    private PublicKey publicKey;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
}
