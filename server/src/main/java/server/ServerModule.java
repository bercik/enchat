package server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import controller.utils.cypher.ServerKeyContainerCreationFailed;
import model.Account;
import model.ChatRoom;
import model.ClientPublicKeyInfo;
import model.containers.permanent.Accounts;
import model.containers.temporary.Logged;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import model.containers.temporary.Rooms;
import rsa.KeyContainer;
import server.listeners.message.InputStreamsHandler;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

/**
 * ServerModule is the class that hold configuration info for Guice.
 * Tells Guice how to create objects, and defined providers.
 *
 * @author Created by tochur on 13.05.15.
 */
public class ServerModule extends AbstractModule {
    /**
     * Configuration function
     */
    @Override
    protected void configure() {
        /* SCOPES - SINGLETONS */
        bind(InputStreamsHandler.class).in(Scopes.SINGLETON);
        bind(KeyContainer.class).in(Singleton.class);

        /* IMPLEMENTATIONS */
        //bind(ServerSocket.class).annotatedWith(Names.named("CUSTOM")).toProvider(SeverSocketProvider.class);

        /* CONSTANTS */
            //Sets the port in which Server listen.
        bindConstant().annotatedWith(Names.named("PORT_NUMBER")).to(50000);
            //Amount of DataInputStreams, that are scanned by server
        bindConstant().annotatedWith(Names.named("MAX_ACTIVE_USER")).to(1000);
            //Amount of Accounts, that may be created.
        bindConstant().annotatedWith(Names.named("ACCOUNT_LIMIT")).to(1000);
            //Amount of max nicks on black list
        bindConstant().annotatedWith(Names.named("BLACK_LIST_MAX_SIZE")).to(100);

    }

    @Provides
    @Named("Server")
    PublicKey getPublicKey(KeyContainer keyContainer) {
        try {
            return keyContainer.getPublicKeyInfo().getPublicKey();
        } catch (Exception e) {
            throw new ServerKeyContainerCreationFailed();
        }
    }

    @Provides
    @Named("Server")
    PrivateKey getPrivateKey(KeyContainer keyContainer) {
        try {
            return keyContainer.getPrivateKeyInfo().getPrivateKey();
        } catch (Exception e) {
            throw new ServerKeyContainerCreationFailed();
        }
    }

    @Provides
    @Named("Accounts")Map<String, Account> getAccountsMap (Accounts accounts){
        return accounts.getMap();
    }


    @Provides
    @Named("IDAccounts")Map<Integer, Account> getMapID_Accounts (Logged logged){
        return logged.getMap();
    }

    @Provides
    @Named("IDPublicKeys")Map<Integer, ClientPublicKeyInfo> getMapID_Accounts (PublicKeys publicKeys){
        return publicKeys.getMap();
    }

    @Provides @Singleton
    ServerSocket getServerSocket(@Named("PORT_NUMBER")Integer PORT){
        System.out.println("Starting server on PORT: " + PORT);
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            return serverSocket;
        } catch (IOException e) {
            System.out.print("Not successful socket creation.");
            return null;
        }
    }
}
