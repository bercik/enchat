package handlers;

/**
 * Created by tochur on 18.04.15.
 *
 * Responsible for handling new clients that want to user newServer.
 *
 * This class Creates new Client object and adding it to ActiveUser collection.
 * That means that newServer will listen for messages from this user.
 */
/*public class NewClientHandler implements Runnable{
    private KeyContainer keyContainer;
    private Socket clientSocket;

    public NewClientHandler(Socket clientSocket){
        this.keyContainer = Server.getInstance().getKeyContainer();
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print("Unable to initialize connection; Public key exchanging failed.");
            e.printStackTrace();
        }
    }

    public void createConnection() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, GeneratingPublicKeyException {
        //Tworzę nowego użytkownika
        User newUser = new User(clientSocket);
        //Wysłanie klucza publicznego
        keyContainer.getPublicKeyInfo().send(newUser.getOutStream());

        //Pobieranie klucza publicznego użytkownika
        PublicKeyInfo clientPublicKeyInfo = new PublicKeyInfo(newUser.getInputStream());
        System.out.println("Odebrano klucz publiczny nowego użytkownika.");
        System.out.println("Modulus: " + clientPublicKeyInfo.getModulus());
        System.out.println("Exponent: " + clientPublicKeyInfo.getExponent());
        //Ustawienie klucza publicznego
        PublicKey publicKey = clientPublicKeyInfo.getPublicKey();
        newUser.setPublicKey(publicKey);

        //Adding new user
        try {
            ActiveUsers.getInstance().addUser(newUser);
        } catch (AlreadyInCollection alreadyInCollection) {
            //Internal newServer error, 2 users got the same socket.
            alreadyInCollection.printStackTrace();
        } catch (OverloadedCannotAddNew overloadedCannotAddNew) {
            EncryptedMessage message = new Messages().serverError().serverOverloaded();
            MessageSender.sendMessage(newUser, message);
        }
    }
}*/
