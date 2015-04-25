package handlers;

import containers.ActiveUsers;
import message.Encryption;
import message.Message;
import message.MessageCreator;
import message.utils.MessageSender;
import messages.IncorrectMessageId;
import messages.MessageId;
import rsa.KeyContainer;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;
import server.Server;
import user.ActiveUser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tochur on 18.04.15.
 *
 * Responsible for handling new clients that want to user server.
 *
 * This class Creates new Client object and adding it to ActiveUser collection.
 * That means that server will listen for messages from this user.
 */
public class NewClientHandler implements Runnable{
    private KeyContainer keyContainer;
    private Socket clientSocket;

    public NewClientHandler(Socket clientSocket){
        this.keyContainer = Server.getInstance().getKeyContainer();
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            createConnection();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public void createConnection() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, SignatureException {
        ActiveUser newActiveUser = new ActiveUser(clientSocket);
        keyContainer.getPublicKeyInfo().send(new DataOutputStream(newActiveUser.getOutStream()));
        PublicKeyInfo clientPublicKeyInfo = null;
        try {
            clientPublicKeyInfo = new PublicKeyInfo(new DataInputStream(newActiveUser.getInputStream()));
        } catch (GeneratingPublicKeyException e) {
            e.printStackTrace();
        }

        System.out.println("Got modulus: " + clientPublicKeyInfo.getModulus());
        System.out.println("Got ex: " + clientPublicKeyInfo.getExponent());
        ActiveUser activeUser = new ActiveUser(clientSocket, clientPublicKeyInfo);
        newActiveUser.setPublicKeyInfo(clientPublicKeyInfo);
        ActiveUsers.getInstance().addUser(activeUser);
        System.out.print("CLIENT SERVED");

        Message message = MessageCreator.createInfoMessage(MessageId.SIGN_UP, 1, "String encoded");
        try {
            MessageSender.sendMessage(activeUser, Encryption.encryptMessage(activeUser, message));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IncorrectMessageId incorrectMessageId) {
            incorrectMessageId.printStackTrace();
        }
    }
}
