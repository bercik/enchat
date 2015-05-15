package newServer;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import controller.utils.ServerKeyContainerCreationFailed;
import newServer.listeners.message.InputStreamsHandler;
import rsa.KeyContainer;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tochur on 13.05.15.
 */
public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        /* SCOPES - SINGLETONS */
        bind(InputStreamsHandler.class).in(Scopes.SINGLETON);

        /* IMPLEMENTATIONS */
        //bind(ServerSocket.class).annotatedWith(Names.named("CUSTOM")).toProvider(SeverSocketProvider.class);

        /* CONSTANTS */
            //Sets the port in which Server listen.
        bindConstant().annotatedWith(Names.named("PORT_NUMBER")).to(50000);
            //Amount of DataInputStreams, that are scanned by server
        bindConstant().annotatedWith(Names.named("MAX_ACTIVE_USER")).to(1000);
    }

    @Provides @Singleton
    ServerSocket getServerSocket(@Named("PORT_NUMBER")Integer PORT){
        System.out.println("Socket from provider: " + PORT);
        try {
            System.out.println("inside try");
            ServerSocket serverSocket = new ServerSocket(PORT);
            return serverSocket;
        } catch (IOException e) {
            System.out.print("Not successful socket creation.");
            return null;
        }
    }
    @Provides
    KeyContainer getKeyContainer() {
        try {
            KeyContainer keyContainer = new KeyContainer();
            return keyContainer;
        } catch (NoSuchAlgorithmException e) {
            throw new ServerKeyContainerCreationFailed();
        } catch (InvalidKeySpecException e) {
            throw new ServerKeyContainerCreationFailed();
        }
    }
}
