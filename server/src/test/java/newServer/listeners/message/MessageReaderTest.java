package newServer.listeners.message;

import message.types.EncryptedMessage;
import message.types.Pack;
import messages.MessageId;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.awt.event.KeyEvent;
import java.io.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MessageReaderTest {
    private static DataInputStream dataInputStream;
    private static String fileName = "Message";
    private static MessageReader messageReader = new MessageReader();

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Test
    public void reading_message_header_from_stream() throws Exception {
        File file = folder.newFile("temporary.txt");

        OutputStream outputStream = new FileOutputStream(file);
        DataOutputStream out = new DataOutputStream(outputStream);

        out.writeInt(1);
        out.writeInt(2);
        out.writeInt(0);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        EncryptedMessage message = messageReader.readFromStream(in);

        assertThat("Incorrect Message ID ", message.getId(), is(MessageId.LOG_IN));
        assertThat("Incorrect Message Error ID", message.getErrorId(), is(MessageId.LOG_IN.createErrorId(2)));
        assertThat("Wrong package amount. ",message.getPackageAmount(), is(0));
    }

    @Test
    public void reading_message_with_content() throws Exception {
        File file = folder.newFile("temporary.txt");

        OutputStream outputStream = new FileOutputStream(file);
        DataOutputStream out = new DataOutputStream(outputStream);

        out.writeInt(1);
        out.writeInt(2);
        out.writeInt(2);
        createAndSendStringInPackage("Sample text (first package)", out);
        createAndSendStringInPackage("Second package.", out);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        EncryptedMessage message = messageReader.readFromStream(in);

        assertThat("Incorrect Message ID ", message.getId(), is(MessageId.LOG_IN));
        assertThat("Incorrect Message Error ID", message.getErrorId(), is(MessageId.LOG_IN.createErrorId(2)));
        assertThat("Wrong package amount. ",message.getPackageAmount(), is(2));
        Pack[] packs = message.getPackages().toArray(new Pack[0]);

        assertThat("Wrong first package content. ",new String(packs[0].getDataArray()),
                is("Sample text (first package)"));
        assertThat("Wrong sign content. ",new String(packs[0].getDataArray()),
                is("Sample text (first package)"));
        assertThat("Wrong second package content. ",new String(packs[1].getDataArray()),
                is("Second package."));
    }

    @Test(expected = EOFException.class)
    public void not_fully_message_in_buffer() throws Exception {
        File file = folder.newFile("temporary.txt");

        OutputStream outputStream = new FileOutputStream(file);
        DataOutputStream out = new DataOutputStream(outputStream);

        out.writeInt(1);
        out.writeInt(2);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        EncryptedMessage message = messageReader.readFromStream(in);

        assertThat("Incorrect Message ID ", message.getId(), is(MessageId.LOG_IN));
        assertThat("Incorrect Message Error ID", message.getErrorId(), is(MessageId.LOG_IN.createErrorId(2)));
    }

    @Test(expected = IOException.class)
    public void stream_was_closed() throws Exception {
        File file = folder.newFile("temporary.txt");

        OutputStream outputStream = new FileOutputStream(file);
        DataOutputStream out = new DataOutputStream(outputStream);

        out.writeInt(1);
        out.writeInt(2);
        out.close();
        out.writeInt(0);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        messageReader.readFromStream(in);
    }

    /*@Test()
    public void stream_delay() throws Exception {
        File file = folder.newFile("temporary.txt");

        OutputStream outputStream = new FileOutputStream(file);
        DataOutputStream out = new DataOutputStream(outputStream);

        new Thread(new MyWriter(1,2,0,out)).start();

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        messageReader.readFromStream(in);
    }*/


    private void createAndSendStringInPackage(String message, DataOutputStream outputStream) throws IOException {
        byte[] array = message.getBytes();
        int length = array.length;

        outputStream.writeInt(length);
        outputStream.write(array);
        outputStream.writeInt(length);
        outputStream.write(array);
    }

    class MyWriter implements Runnable{
        int ID; int error; int amount; DataOutputStream out;
        MyWriter(int ID, int error, int amount, DataOutputStream out){this.ID = ID; this.error = error; this.amount = amount; this.out = out;}

        @Override
        public void run() {
            try {
                out.writeInt(ID);
                out.writeInt(error);
                Thread.sleep(300);
                out.writeInt(amount);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}