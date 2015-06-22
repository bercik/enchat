package app_info;

import app_info.exceptions.BadConfigurationFileException;
import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;
import util.config.NetworkValidator;

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
    }

    public static void init() throws IOException,
            GeneratingPublicKeyException, BadConfigurationFileException
    {
        if (instance == null)
        {
            instance = new Configuration();
            try
            {
                instance.loadFromFile();
            }
            catch (BadConfigurationFileException | IOException ex)
            {
                // jeżeli nie udało się odczytać z pliku to ustawiamy domyślne
                // wartości i rzucamy wyjątek dalej
                instance.port = DEFAULT_PORT;
                instance.serverAddress = DEFAULT_SERVER_ADDRESS;

                throw ex;
            }
        }
    }

    //funkcja którą będziemy wywoływać gdy będziemy potrzbowali informacji o konfiguracji
    public static Configuration getInstance()
    {
        // w tym momencie powinna być już utworzona 
        // instancja klasy Configuration poprzez wywołanie metody init()
        if (instance == null)
        {
            String msg = "instance in Configuration singleton is null. "
                    + "You should call init() method first";
            throw new ExceptionInInitializerError(msg);
        }

        return instance;
    }

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static String getCommandPrefix()
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

    // funkcja zwracająca ścieżkę bezwzględną do pliku konfiguracyjnego
    private String getConfigFilePath()
    {
        // ugly code to read configuration text file
        // it recognize path to .jar file or classes directory
        // and than manipulate to get the path with conf.txt file
        // conf.txt file is in the folder above target folder
        URL location = Configuration.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = location.getFile();
        jarPath = jarPath.substring(0, jarPath.lastIndexOf('/'));
        return jarPath + FILE_PATH;
    }

    private void writeAddressAndPortToFile(BufferedWriter writer,
            String address, int port) throws IOException
    {
        writer.write(address);
        writer.write("\n");
        writer.write(Integer.toString(port));
        writer.write("\n");
    }

    //funkcja ładuje informacje z pliku, który jeżeli nie istnieje jest tworzony
    private void loadFromFile() throws IOException,
            GeneratingPublicKeyException, BadConfigurationFileException
    {
        String filePath = getConfigFilePath();

        // create directorys if necessary
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        if (file.createNewFile())
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
            {
                writeAddressAndPortToFile(writer, DEFAULT_SERVER_ADDRESS,
                        DEFAULT_PORT);

                port = DEFAULT_PORT;
                serverAddress = DEFAULT_SERVER_ADDRESS;
            }
        }
        else
        {
            try (BufferedReader reader
                    = new BufferedReader(new FileReader(file)))
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

                if (serverAddress == null
                        || !NetworkValidator.validateIPv4Address(serverAddress)
                        || !NetworkValidator.validatePort(port))
                {
                    throw new BadConfigurationFileException();
                }
            }
        }
    }

    // funkcja która ustawia nowy adres i port i zapisuje je do pliku
    public void SetAndSaveToFile(String newServerAddress, int newPort)
            throws IOException
    {
        // przypisujemy nowe wartości
        serverAddress = newServerAddress;
        port = newPort;

        // otwieramy plik
        String filePath = getConfigFilePath();
        File file = new File(filePath);
        // wpisujemy nowe wartości
        // nadpisujemy stary
        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter(file, false)))
        {
            // wpisujemy nowe wartości
            writeAddressAndPortToFile(writer, serverAddress, port);
        }
    }

    //zmienna którą w razie potrzeby będziemy zwracać
    private static Configuration instance = null;

    // prefiks każdej komendy
    private static final String commandPrefix = "/";
    //rozmiar naszej konsoli
    private static final int width = 122;
    private static final int height = 36;

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
