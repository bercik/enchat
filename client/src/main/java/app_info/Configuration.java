package app_info;

import app_info.exceptions.BadConfigurationFileException;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;
import sun.net.util.IPAddressUtil;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public final class Configuration
{
    //konstruktor prywatny potrzebny do wczytania odpowiednich informacji z pliku
    private Configuration() throws IOException, GeneratingPublicKeyException, 
            BadConfigurationFileException
    {
        width = 122;
        height = 36;
        loadFromFile();
    }

    //funkcja którą będziemy wywoływać gdy będziemy potrzbowali informacji o konfiguracji
    public static Configuration getInstance()
    {
        if (instance == null)
        {
            try
            {
                instance = new Configuration();
            }
            catch (Exception ex)
            {
                throw new ExceptionInInitializerError(ex);
            }
        }

        return instance;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getCommandPrefix()
    {
        return commandPrefix;
    }

    public String getServerAddress()
    {
        return serverAddress;
    }

    public int getPort()
    {
        return port;
    }

    public PublicKeyInfo getServerPublicKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return new PublicKeyInfo(serverPublicKeyInfo);
    }

    //funkcja ładuje informacje z pliku który uprzednio jest wyszukiwany przy pomocy
    //funkcji findFile
    public void loadFromFile() throws IOException, GeneratingPublicKeyException, BadConfigurationFileException
    {
        // ugly code to read configuration text file
        // it recognize path to .jar file or classes directory
        // and than manipulate to get the path with conf.txt file
        // conf.txt file is in the folder above target folder
        URL location = Configuration.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = location.getFile();
        jarPath = jarPath.substring(0, jarPath.lastIndexOf('/'));
        String filePath = jarPath + FILE_PATH;

        // create directorys if necessary
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        if (file.createNewFile())
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
            {
                writer.write(DEFAULT_SERVER_ADDRESS);
                writer.write("\n");
                writer.write(Integer.toString(DEFAULT_PORT));
                writer.write("\n");

                port = DEFAULT_PORT;
                serverAddress = DEFAULT_SERVER_ADDRESS;
            }
        }
        else
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(file)))
            {
                try
                {
                    serverAddress = reader.readLine();
                    port = Integer.parseInt(reader.readLine());
                }
                catch (Exception ex)
                {
                    throw new BadConfigurationFileException(ex);
                }
                
                if (serverAddress == null || 
                        !IPAddressUtil.isIPv4LiteralAddress(serverAddress))
                {
                    throw new BadConfigurationFileException();
                }
            }
        }
    }

    //zmienna którą w razie potrzeby będziemy zwracać
    private static Configuration instance = null;

    // prefiks każdej komendy
    private final String commandPrefix = "/";
    //rozmiar naszej konsoli
    private int width = 122;
    private int height = 36;

    //zmienne te będą wczytywane z pliku
    private String serverAddress;
    private int port;
    private PublicKeyInfo serverPublicKeyInfo;

    // domyślne wartości
    private static final int DEFAULT_PORT = 50000;
    private static final String DEFAULT_SERVER_ADDRESS = "127.0.0.1";

    //składowa która przechowuje nazwę pliku w którym znajdują się informacje 
    // o porcie i adresie serwera
    private static final String FILE_PATH = "/data/conf.txt";
}
