package app_info;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class Configuration {
    //konstruktor prywatny potrzebny do wczytania odpowiednich informacji z pliku
    private Configuration() throws IOException, GeneratingPublicKeyException {
        width = 122;
        height = 36;
        loadFromFile();
    }

    //funkcja którą będziemy wywoływać gdy będziemy potrzbowali informacji o konfiguracji
    public static Configuration getInstance() {
        if(instance == null) {
            try {
                instance = new Configuration();
            }
            catch (Exception ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        
        return instance;
    }

    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public String getCommandPrefix() {
        return commandPrefix;
    }
    
    public String getServerAddress() {
        return serverAddress;
    }
    
    public int getPort() {
        return port;
    }
    
    public PublicKeyInfo getServerPublicKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new PublicKeyInfo(serverPublicKeyInfo);
    }



    //funkcja ładuje informacje z pliku który uprzednio jest wyszukiwany przy pomocy
    //funkcji findFile
    public void loadFromFile() throws IOException, GeneratingPublicKeyException {
        //ten fragment kodu został zakomentowany do czasu kiedy zostanie napisana funkcja
        //serwera która będzie zapisywać adres serwera i jego port do pliku

        this.findFile(fileName, root);

        //stworzenie strumienia do
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

        serverAddress = in.readLine();

        port = Integer.parseInt(in.readLine());

    }

    //funkcja szukająca naszego pliku
    private void findFile(String name, File file) {
        File[] list = file.listFiles();

        if(list != null)
            for(File fil : list) {
                if(fil.isDirectory()) {
                    findFile(name, fil);
                }
                else if(name.equalsIgnoreCase(fil.getName())) {
                    path =  fil.getParentFile().toString() + "/" + name;
                    break;
                }
            }
    }

    //zmienna którą w razie potrzeby będziemy zwracać
    private static Configuration instance = null;

    // prefiks każdej komendy
    private String commandPrefix = "/";
    //rozmiar naszej konsoli
    private int width = 122;
    private int height = 36;

    //zmienne te będą wczytywane z pliku
    private static String serverAddress;
    private static int port;
    private static PublicKeyInfo serverPublicKeyInfo;

    //zmienna pomocnicza przechowująca nazwę pliku w którym przechowujemy
    //adres serwera, numer portu oraz modulus i exponent klucza publicznego serwera(wersja "żeby działało")
    private String path = "file";

    //składowa która przechowuje nazwę pliku w którym znajdują się informacje o porcie i adresie serwera
    private static final String fileName = "ConfigurationEnchat.txt";
    //miejsce od którego rozpoczynamy nasze poszukiwania pliku
    private static final File root = new File("/home");


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, GeneratingPublicKeyException {
        Configuration configuration = Configuration.getInstance();
        System.out.println("Adres załadowany z pliku : " + configuration.getServerAddress() + " oraz jego port : " + configuration.getPort());
        System.out.println("Szerokość konsoli : " + configuration.getWidth() + " oraz jest wysokosc : " + configuration.getHeight());
    }

}
