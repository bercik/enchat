/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.conversation;

import java.io.FileInputStream;
import java.io.IOException;
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
import util.PathGetter;

/**
 *
 * @author robert
 */
public class ConversationFileReader
{
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
    private int offset = 0;

    public List<Message> read(String fileName, String password)
            throws ConversationFileReadException
    {
        try
        {
            offset = 0;

            String jarPath = new PathGetter().getAbsolutePathToJarDir();
            String folderPath = jarPath + PATH;
            String filePath = folderPath + fileName;

            List<Message> result = new ArrayList<>();

            try (FileInputStream fis = new FileInputStream(filePath))
            {
                byte[] byteArray = new byte[fis.available()];
                fis.read(byteArray);

                // decrypt AES
                byte[] original = decrypt(byteArray, password);

                reconstructMessages(result, original);
                
                return result;
            }
        }
        catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException 
                | InvalidKeyException | IllegalBlockSizeException 
                | BadPaddingException ex)
        {
            throw new ConversationFileReadException(ex);
        }
    }

    private byte[] decrypt(byte[] byteArray, String password) 
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

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(byteArray);
    }

    private void reconstructMessages(List<Message> messages, byte[] byteArray)
    {
        int numberOfMessages = readInt(byteArray);

        while (numberOfMessages-- > 0)
        {
            try
            {
                String username = readString(byteArray);
                String message = readString(byteArray);
                LocalTime timeFrom = readTime(byteArray);
                LocalTime timeTo = readTime(byteArray);

                Message m = new Message(message, username, timeFrom, timeTo);
                messages.add(m);
            }
            catch (UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }

    private int readInt(byte[] byteArray)
    {
        byte[] intByteArray = Arrays.copyOfRange(byteArray, offset, offset + 4);
        offset += 4;
        return ByteBuffer.wrap(intByteArray).getInt();
    }

    private String readString(byte[] byteArray)
            throws UnsupportedEncodingException
    {
        int length = readInt(byteArray);
        byte[] stringByteArray = Arrays.copyOfRange(byteArray, offset,
                offset + length);
        offset += length;
        return new String(stringByteArray, RSA.STRING_CODING);
    }

    private LocalTime readTime(byte[] byteArray)
    {
        int hour = readInt(byteArray);
        int minute = readInt(byteArray);

        return new LocalTime(hour, minute);
    }
}
