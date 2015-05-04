package app_info;

import junit.framework.TestCase;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by mateusz on 03.05.15.
 */
public class ConfigurationTest extends TestCase {

    public void testConfiguration() throws Exception {

        FileOutputStream fileOutputStream = new FileOutputStream("file.txt");
        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);

        //zapisywanie adresu i portu serwera do pliku w postaci tablicy bajtów
        String serverAddress = "123.154.3.4";
        outputStream.writeUTF(serverAddress);

        Integer port = 50000;
        outputStream.writeUTF(port.toString());

        System.out.println("Adres wysłany do pliku : " + serverAddress + " oraz jego port : " + port);

        //test funkcji w klasie Configuration
        Configuration configuration = Configuration.getInstance();
        System.out.println("Adres załadowany z pliku : " + configuration.getServerAddress() + " oraz jego port : " + configuration.getPort());
        System.out.println("Szerokość konsoli : " + configuration.getWidth() + " oraz jest wysokosc : " + configuration.getHeight());

    }
}