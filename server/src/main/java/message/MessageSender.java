package message;

import java.net.Socket;

/**
 * Created by tochur on 16.04.15.
 */
public class MessageSender {
    private static MessageSender messageSender;

    public static MessageSender getInstance(){
        if( messageSender == null )
            messageSender = new MessageSender();
        return messageSender;
    }

    public static void sendMessage(Socket socket, Message message){
        //out = new DataOutputStream(clientSocket.getOutputStream());
        //in = new DataInputStream(clientSocket.getInputStream());
    }

    private MessageSender(){}
}
