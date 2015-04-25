package server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tochur on 13.04.15.
 */
public class StartServer {
    public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        Server server = Server.getInstance();
        System.out.println("Starting server from main");
        server.standardServerStart();
    }
}
