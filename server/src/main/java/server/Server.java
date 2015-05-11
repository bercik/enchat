package server;

import handlers.NewClientListener;
import handlers.NewMessageScanner;
import rsa.KeyContainer;

import java.io.*;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Server {
    private final int PORT = 50000;
    private final String IP = "10.20.101.183";
    private static Server server = null;
    private ServerSocket serverSocket;
    private KeyContainer keyContainer;


    public KeyContainer getKeyContainer(){
        return this.keyContainer;
    }

    /*This function let to run the server in standard way.*/
    public void standardServerStart() throws IOException {
        //createConfFile("file.txt");
        /*Scanning for new clients*/
        NewClientListener clientListener = new NewClientListener(serverSocket);
        new Thread(clientListener).start();

        /*Scanning for new messages*/
        NewMessageScanner newMessageScanner = new NewMessageScanner();
        new Thread(newMessageScanner).start();
    }

    public void startListeningNewMessagesFromUser(){
        NewMessageScanner newMessageScanner = new NewMessageScanner();
        new Thread(newMessageScanner).start();
    }


    /**
     * Creating new server instance (generating key, setting port, and writing configuration to file.
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private Server() throws InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            serverSocket = new ServerSocket(PORT);
            keyContainer = new KeyContainer();
        } catch (IOException e) {
            System.err.println("Could not open port: " + PORT);
            System.exit(1);
        }
    }
    /*Singleton*/
    public static Server getInstance() {
        if( server == null)
            try {
                server = new Server();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        return server;
    }

    /*Writing configuration into the file, */
    private void createConfFile(String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        DataOutputStream out = new DataOutputStream(fileOutputStream);
        out.writeInt(IP.getBytes().length);
        out.write(IP.getBytes());
        out.writeInt(PORT);

    }

}


