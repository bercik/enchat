/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.conversation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.joda.time.LocalTime;
import rsa.RSA;

/**
 *
 * @author robert
 */
public class ConversationFileSaver
{
    private static final int MAX_MESSAGES_SAVE = 4;
    private static final String PATH = "/conversations/";

    // <-- struktura konwersacji -->
    // ilość wiadomości (int)
    // wiadomość1
    // wiadomość2
    // ...
    // wiadomośćN
    //
    // <-- struktura wiadomości -->
    // dł. ciągu username (int)
    // ciąg username (byte[])
    // dł. ciągu message (int)
    // ciąg message (byte[])
    // godzina rozpoczęcia (int)
    // minuta rozpoczęcia (int)
    // godzina zakończenia (int)
    // minuta zakończenia (int)
    public void save(List<Message> messages, String fileName, String password)
            throws ConversationFileSaveException
    {
        String jarPath = ConversationFileSaver.class.getProtectionDomain().
                getCodeSource().getLocation().getPath();
        jarPath = jarPath.substring(0, jarPath.lastIndexOf('/'));
        String folderPath = jarPath + PATH;
        String filePath = folderPath + fileName;

        new File(folderPath).mkdirs();

        try (FileOutputStream fos = new FileOutputStream(filePath, false))
        {
            byte[] byteAray = constructByteArray(messages);

            // encrypt with AES
            byte[] encrypted = encrypt(byteAray, password);
            
            fos.write(encrypted);
        }
        catch (Exception ex)
        {
            throw new ConversationFileSaveException(ex);
        }
    }

    private byte[] encrypt(byte[] byteArray, String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException
    {
        byte[] key = password.getBytes(RSA.STRING_CODING);
        key = Arrays.copyOf(key, 16); // use only first 128 bit

        SecretKeySpec secretKeySpec = new SecretKeySpec(key,
                RSA.CONVERSATION_SAVE_ALGORITHM_NAME);

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance(
                RSA.CONVERSATION_SAVE_ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        return cipher.doFinal(byteArray);
    }

    private byte[] constructByteArray(List<Message> messages)
    {
        List<Byte> result = new ArrayList<>();

        int indx1 = Math.max(messages.size() - MAX_MESSAGES_SAVE, 0);
        int indx2 = messages.size();
        messages = messages.subList(indx1, indx2);

        // add number of messages
        addInt(result, messages.size());

        // create bytes for all messages
        for (Message m : messages)
        {
            try
            {
                // add username
                addString(result, m.getUsername());
                // add message
                addString(result, m.getMessage());
                // add start time
                addTime(result, m.getDateFrom());
                // add end time
                addTime(result, m.getDateTo());
            }
            catch (UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        // convert list to byte array and return
        byte[] bResult = new byte[result.size()];
        for (int i = 0; i < result.size(); ++i)
        {
            bResult[i] = result.get(i);
        }

        return bResult;
    }

    private void addTime(List<Byte> array, LocalTime time)
    {
        addInt(array, time.getHourOfDay());
        addInt(array, time.getMinuteOfHour());
    }

    private void addString(List<Byte> array, String s)
            throws UnsupportedEncodingException
    {
        byte[] sBytes = s.getBytes(RSA.STRING_CODING);
        int length = sBytes.length;
        addInt(array, length);
        addBytes(array, sBytes);
    }

    private void addInt(List<Byte> array, int i)
    {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        addBytes(array, bb.array());
    }

    private void addBytes(List<Byte> array, byte[] bArray)
    {
        for (byte b : bArray)
        {
            array.add(b);
        }
    }
}
