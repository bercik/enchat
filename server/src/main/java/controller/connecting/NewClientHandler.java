package controller.connecting;

import com.google.inject.Inject;
import newServer.listeners.message.InputStreamsHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by tochur on 13.05.15.
 *
 * This class is responsible for actualization state of users that can exchange information with newServer
 *
 * !!!!!!!!!!!!! MODIFICATION NEEDED !!!!!!!!!!!! - add  model state change.
 */
public class NewClientHandler implements Runnable {
    private Socket clientSocket;
    private static Integer ID = 0;

    private InputStreamsHandler inputStreamsListener;

    @Inject
    public NewClientHandler(InputStreamsHandler inputStreamsListener){
        this.inputStreamsListener = inputStreamsListener;
    }

    public void setParameters(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try{
            DataInputStream inputStream = new DataInputStream( clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream( clientSocket.getOutputStream());

            inputStreamsListener.addStreamToScan(ID++, inputStream);
            /*

                Add to model (input stream)
                Add to model (output stream)

            */
        } catch (IOException e) {
            System.out.println("Enable to open streams on new client socket. Connection rejected.");
            e.printStackTrace();
        }

    }
}
