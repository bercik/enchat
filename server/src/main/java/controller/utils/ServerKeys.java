package controller.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import rsa.KeyContainer;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by tochur on 15.05.15.
 *
 * Singleton object, that wraps Server Keys.
 */
@Singleton
public class ServerKeys {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Inject
    public ServerKeys(@Named("Server")PublicKey publicKey, @Named("Server") PrivateKey privateKey){

    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
