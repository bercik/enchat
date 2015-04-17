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
            System.err.println("Could not listen on port: " + PORT);
            System.exit(1);
        }
    }
    /*Singleton*/
    public static Server getInstance(){
        if( server == null)
            server = new Server();
        return server;
    }

    /*Wprowadza serwer w stan nasłuchiwania, nowy klient jest przełączeny do handlera a Server nadal nasłuchuje. */
    public void start(){
        new Thread(new ViewThread(stdOut)).start();
        startListeningNewClientsRequest();
    }

    /*Starting new thread showing actual server state.*/
    private ViewThread startViewThread(){
        ViewThread viewThread = new ViewThread(stdOut);
        viewThread.run();
        return viewThread;
    }

    /*Waiting for Client and initializing interaction with him.*/
    private void startListeningNewClientsRequest(){
        Socket clientSocket;
        while(true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Got the client");
                switchNewClientToHandler(clientSocket);
            } catch (IOException e) {
                System.err.println("Clients acceptation failed - io exception");
            }
        }
    }

    /*Metoda przekazuje klienta - reprezentowanego przez clientSocket do hendlera, który przeprowadzi go przez */
    private void switchNewClientToHandler(Socket clientSocket){
        Thread newClientHandler = new Thread((Runnable) new ConnectHandler(clientSocket));
        newClientHandler.start();
    }
}

