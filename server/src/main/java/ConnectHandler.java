import rsa.PublicKeyInfo;
import rsa.RSA;
import rsa.exceptions.GeneratingPublicKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

/**
 * Created by tochur on 13.04.15.
 */

public class ConnectHandler implements Runnable {
    protected DataOutputStream out;
    protected DataInputStream in;

    /*Klucz publiczny klienta*/
    private PublicKeyInfo keyInfo;

    public ConnectHandler(Socket clientSocket){
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            scanForKey();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            sendEncryptedMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    private void sendMessage() throws IOException {
        System.out.print("Sending message");
        byte[] array = "MEssage".getBytes();
        out.writeInt(array.length);
        out.write(array);
    }


    private void sendEncryptedMessage() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] array = "1 2 3 4 5".getBytes();
        byte[] encrypted = new byte[array.length];
        try {
            encrypted = RSA.encrypt(array, keyInfo.getPublicKey());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        out.writeInt(encrypted.length);
        out.write(encrypted);
    }

    /*Checks the buffer state returns string - answer or null - id buffer is empty.*/
    /*protected void scanForAnswerTwo(){
        int messageId = 0;
        try {
            if(in.available() ==)
            messageId = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(messageId);
    }*/

    /*Checks the buffer state returns string - answer or null - id buffer is empty.*/
    protected void scanForAnswer(){
        int messageId = 0;
        int packageNumbers;
        ArrayList<byte[]> arrays = new ArrayList<byte[]>();
        try{
            messageId = in.readInt();
            packageNumbers = in.readInt();
            for(int i = 0; i<packageNumbers; i++){
                int packageLen = in.readInt();
                byte[] nextPack = new byte[packageLen];
                in.readFully(nextPack);
                arrays.add(nextPack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Received info from client.");
        System.out.println(messageId);
        for(int i = 0; i<arrays.size(); i++){
            try {
                String message = new String(arrays.get(i), "UTF-8");
                System.out.println(message);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        /*for(byte[] array: arrays){
            try{
                String s = array.toString("UTF-8");
            }catch (Exception e){
            }

            /*for(byte b: array){
                System.out.print(b);
            }*/
        //}
    }

    protected void scanForKey() throws InvalidKeySpecException, ClassNotFoundException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        try {
            this.keyInfo = new PublicKeyInfo(in);
        } catch (GeneratingPublicKeyException e) {
            e.printStackTrace();
        }
    }

    /*THOSE 3 METHODS RETURNS ACTION TO PERFORM BY SERVER IN NEXT LOOP STEP.*/
    /*Called to send info to user.*/
   /* protected void sendNextMessage(){
        System.out.print("getNextMessage()");
        out.println(interactionProtocol.getNextMessage());
    }*/

    /*Called to check input buffer and send received message to protocol object.*/
  /*  protected void waitForAnswer(){
        System.out.println("waitForMessage()");
        String answer = this.scanForAnswer();
        if(answer != null) {
            System.out.println("gotMessage(): " + answer);
            interactionProtocol.handleNextMessage(answer);
        }
    }

    /*Ends interaction, calls protocol finalizing method.*/
   /* protected void endConversation(){
        System.out.println("endConversation()");
        interactionProtocol.finalizeInteraction();
    }*/


    /*THOSE 3 METHODS RETURNS ACTION TO PERFORM BY SERVER IN NEXT LOOP STEP.*/
    /*Called to send info to user.*/
   /* protected void sendNextMessage(){
        System.out.print("getNextMessage()");
        out.println(interactionProtocol.getNextMessage());
    }*/

    /*Called to check input buffer and send received message to protocol object.*/
  /*  protected void waitForAnswer(){
        System.out.println("waitForMessage()");
        String answer = scanForAnswer();
        if(answer != null) {
            System.out.println("gotMessage(): " + answer);
            interactionProtocol.handleNextMessage(answer);
        }
    }*/

    /*Ends interaction, calls protocol finalizing method.*/
  /*  protected void endConversation(){
        System.out.println("endConversation()");
        interactionProtocol.finalizeInteraction();
    }*/

}
