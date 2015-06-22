package model;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import rsa.KeyContainer;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Singleton object, that wraps Server Key.
 *
 * @author Created by tochur on 15.05.15.
 */
@Singleton
public class ServerKeys {
    private KeyContainer keyContainer;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * Creates new ServerKeysKit
     */
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

    /**
     * Returns server PublicKey.
     * @return server PublicKey.
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Returns server PrivateKey.
     * @return server PrivateKey.
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
