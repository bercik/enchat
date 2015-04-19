package message;

import org.junit.Test;
import user.ActiveUser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageReaderTest {
    private final String fileName = "message";

    /*This is a bit integration test, but i needed it to make sure, that message is read correctly */
    @Test
    public void IsMessageReadCorrectlyFromStream() throws Exception {
      //before
        //creating file with message.
        File file = new File(fileName);
        if (file.exists())
            file.delete();
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);


        outputStream.writeInt(12);
        outputStream.writeInt(0);
        outputStream.writeInt(2);

        /*Putting packages into stream*/
        String message = "Testing message reader successfulżźó&@!~~!!!";
        byte[] byteMessage = message.getBytes();
        outputStream.writeInt(byteMessage.length);
        outputStream.write( byteMessage );


        String sign = "Sign read successfully";
        byte[] byteSign = sign.getBytes();
        outputStream.writeInt(byteSign.length);
        outputStream.write( byteSign );

        //Second pack
        outputStream.writeInt(byteMessage.length);
        outputStream.write( byteMessage );

        outputStream.writeInt(byteSign.length);
        outputStream.write( byteSign );


        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream inputStream = new DataInputStream(fileInputStream);

        //mocking Socket & activeUser
        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(inputStream);
        ActiveUser activeUser = mock(ActiveUser.class);
        when(activeUser.getSocket()).thenReturn(socket);

        //when
        MessageReader messageReader = new MessageReader();
        Message readMessage = messageReader.readMessage(activeUser);


        //then
        assertThat(readMessage.getId(), is(12));
        assertThat(readMessage.getErrorId(), is(0));
        assertThat(readMessage.getPackageAmount(), is(2));
        //ArrayList<Pack> packs = new ArrayList<Pack>();
        ArrayList<Pack> packs = readMessage.getPackages();
        assertThat(packs.size(),is(2));
        for(Pack pack: packs){
            assertThat(pack.getDataArray().length, is(byteMessage.length));
            assertThat(pack.getDataArray(), is(byteMessage));
            assertThat(pack.getSignArray(), is(byteSign));
        }
    }
}