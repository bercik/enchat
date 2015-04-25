package message;

import message.utils.MessageReader;
import message.utils.MessageSender;
import messages.IncorrectMessageId;
import messages.MessageId;
import org.junit.Test;
import user.ActiveUser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageSendingIntegrationTest {
    private final String fileName = "message";

    /*This is a bit integration test, but i needed it to make sure, that message is read correctly */
    @Test
    public void IsMessageReadCorrectlyFromStream() throws Exception {
      //before

        //creating file with message.
        File file = createNewFile(fileName);

        //Output Stream
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);

        //mocking Socket & activeUser
        Socket socket = mock(Socket.class);
        when(socket.getOutputStream()).thenReturn(outputStream);

        ActiveUser activeUser = mock(ActiveUser.class);
        //when(activeUser.getSocket()).thenReturn(socket);

        //message to send
        EncryptedMessage message = createTestMessage();


        //when
        MessageSender.getInstance().sendMessage(activeUser, message);

        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream inputStream = new DataInputStream(fileInputStream);
        when(socket.getInputStream()).thenReturn(inputStream);
        MessageReader messageReader = new MessageReader();
        EncryptedMessage readMessage = messageReader.readMessage(activeUser);


        //then
        assertThat(readMessage.getId(), is(MessageId.createMessageId(12)));
        assertThat(readMessage.getErrorId(), is(0));
        assertThat(readMessage.getPackageAmount(), is(2));
        List<Pack> readPacks = readMessage.getPackages();
        assertThat(message.getPackages().size(),is(2));
        for(Pack pack: readPacks){
            assertThat(pack.getDataArray(), is("Testing message with some symbols: żźóńś@!#$%^&*()+\\/=jf".getBytes()));
            assertThat(pack.getSignArray(), is("sign".getBytes()));
        }
    }

    private File createNewFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
        file.createNewFile();
        return file;
    }

    private EncryptedMessage createTestMessage() throws IncorrectMessageId {
        ArrayList<Pack> packs = new ArrayList<Pack>();
        for(int i = 0; i < 2; i++){
            packs.add(new Pack("Testing message with some symbols: żźóńś@!#$%^&*()+\\/=jf".getBytes(), "sign".getBytes()));
        }
        EncryptedMessage message = new EncryptedMessage(12, 0, 2, packs);
        return message;
    }
}