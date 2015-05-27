package app_info;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public final class Configuration
{
    //konstruktor prywatny potrzebny do wczytania odpowiednich informacji z pliku
    private Configuration() throws IOException, GeneratingPublicKeyException
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
    public void loadFromFile() throws IOException, GeneratingPublicKeyException
    {
        // TODO change that conf.txt file is in the same folder as .jar file
        
        // ugly code to read configuration text file
        // it recognize path to .jar file or classes directory
        // and than manipulate to get the path with conf.txt file
        // conf.txt file is in the folder above target folder
        URL location = Configuration.class.getProtectionDomain().getCodeSource().getLocation();
        String path = location.getFile();

        // jeżeli jesteśmy w IDE to ucinamy końcówkę, żeby być w tym samym
        // folderze co plik .jar
        String classes = "classes/";
        if (path.endsWith(classes))
        {
            int end = path.length() - classes.length();
            path = path.substring(0, end);
        }
        // inaczej jeżeli odpalamy plik .jar to odcinamy końcówkę z jego nazwą
        else if (path.endsWith(".jar"))
        {
            int end = path.lastIndexOf('/');
            path = path.substring(0, end + 1);
        }
        // wychodzimy z folder target
        String target = "target/";
        if (path.endsWith(target))
        {
            int end = path.length() - target.length();
            path = path.substring(0, end);
        }

        path += filePath;

        BufferedReader in = new BufferedReader(new FileReader(path));

        serverAddress = in.readLine();
        port = Integer.parseInt(in.readLine());
    }

    //zmienna którą w razie potrzeby będziemy zwracać
    private static Configuration instance = null;

    // prefiks każdej komendy
    private String commandPrefix = "/";
    //rozmiar naszej konsoli
    private int width = 122;
    private int height = 36;

    //zmienne te będą wczytywane z pliku
    private String serverAddress;
    private int port;
    private PublicKeyInfo serverPublicKeyInfo;

    //składowa która przechowuje nazwę pliku w którym znajdują się informacje o porcie i adresie serwera
    private static final String filePath = "data/conf.txt";
}
