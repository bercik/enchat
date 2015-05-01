package handlers;

import containers.ActiveUsers;
import rsa.KeyContainer;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;
import server.Server;
import user.ActiveUser;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print("Unable to initialize connection; Public key exchanging failed.");
            e.printStackTrace();
        }
    }

    public void createConnection() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, GeneratingPublicKeyException {
        /*Tworzę nowego użytkownika*/
        ActiveUser newActiveUser = new ActiveUser(clientSocket);
        //Wysłanie klucza publicznego
        keyContainer.getPublicKeyInfo().send(newActiveUser.getOutStream());

        //Pobieranie klucza publicznego użytkownika
        PublicKeyInfo clientPublicKeyInfo = new PublicKeyInfo(newActiveUser.getInputStream());
        System.out.println("Odebrano klucz publiczny nowego użytkownika.");
        System.out.println("Modulus: " + clientPublicKeyInfo.getModulus());
        System.out.println("Exponent: " + clientPublicKeyInfo.getExponent());
        //Ustawienie klucza publicznego
        PublicKey publicKey = clientPublicKeyInfo.getPublicKey();
        newActiveUser.setPublicKey(publicKey);

        //Adding new user
        ActiveUsers.getInstance().addUser(newActiveUser);
    }
}
