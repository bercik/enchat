package model;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import newServer.KeyCreationError;
import rsa.KeyContainer;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tochur on 15.05.15.
 *
 * Singleton object, that wraps Server Keys.
 */
@Singleton
public class ServerKeys {
    private KeyContainer keyContainer;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Inject
    public ServerKeys(){
        try {
            this.keyContainer = new KeyContainer();
            this.publicKey = keyContainer.getPublicKeyInfo().getPublicKey();
            this.privateKey = keyContainer.getPrivateKeyInfo().getPrivateKey();
        } catch (Exception e) {
            System.out.print("Unable to start server, cause CANNOT GENERATE SERVER KEYS.");
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
