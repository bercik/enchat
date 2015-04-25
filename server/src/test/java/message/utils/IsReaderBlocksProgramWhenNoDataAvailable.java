package message.utils;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class IsReaderBlocksProgramWhenNoDataAvailable {
    private final String fileName = "message";

    /*This is a bit integration test, but i needed it to make sure, that message is read correctly */
    @Test
    public void IsBlocked() throws Exception {
        //before

        //creating file with message.
        File file = createNewFile(fileName);

        //Output Stream
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream inputStream = new DataInputStream(fileInputStream);

        outputStream.writeInt(666);
        System.out.println("Zczytuję inta co go nie ma");
        int result = -1;
        System.out.println("Available bytes: " + inputStream.available());
        if ( inputStream.available() == 4) {
            result = inputStream.readInt();
        }
        System.out.print("Zczytałęm");


        System.out.println(result);

    }

    private File createNewFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
        file.createNewFile();
        return file;
    }
}