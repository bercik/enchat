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
        System.out.println("Configuration test");
        //test funkcji w klasie Configuration
        Configuration configuration = Configuration.getInstance();
        assertEquals("Referencje obiektów klasy Configuration niepoprawne",configuration.getField("instance") ,configuration);
        assertEquals("Wysokość kosnoli niepoprawna", 36, configuration.getHeight());
        assertEquals("Szerokość konsoli niepoprawna", 122, configuration.getWidth());
        assertEquals("Adres serwera niepoprawny", configuration.getField("serverAddress"), configuration.getServerAddress());
        assertEquals("Port niepoprawny", configuration.getField("port"), configuration.getPort());
    }
}