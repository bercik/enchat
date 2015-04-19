import handlers.NewClientHandler;
import view.ViewThread;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT = 50000;
    private static Server server = null;
    private ServerSocket serverSocket;
    private PrintStream stdOut = System.out;

    private Server(){
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Could not open port: " + PORT);
            System.exit(1);
        }
    }
    /*Singleton*/
    public static Server getInstance(){
        if( server == null)
            server = new Server();
        return server;
    }

    /*This function let to run the server in standard way.*/
    public void standardServerStart(){
        new Thread(new ViewThread(stdOut)).start();
        startListeningNewClientsRequest();
        startListeningNewMessagesFromUser();
    }

    /*Waiting for Client and initializing interaction with him.*/
    public void startListeningNewClientsRequest(){
        Socket clientSocket;
        while(true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Got the client");
                NewClientHandler.addNewClient(clientSocket);
                System.out.println("New client connected.");
            } catch (IOException e) {
                System.err.println("Clients acceptation failed - io exception");
            }
        }
    }

    public void startListeningNewMessagesFromUser(){
        NewMessageScanner newMessageScanner = new NewMessageScanner();
        newMessageScanner.run();
    }


}

