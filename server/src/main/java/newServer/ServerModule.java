package newServer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import newServer.listeners.INewClientListener;
import newServer.listeners.NewClientListener;

/**
 * Created by tochur on 13.05.15.
 */
public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        /* IMPLEMENTATIONS */
        bind(INewClientListener.class).to(NewClientListener.class);

        /* CONSTANTS */
            //Sets the port in which Server listen.
        bindConstant().annotatedWith(Names.named("PORT_NUMBER")).to(50000);
            //Amount of DataInputStreams, that are scanned by server
        bindConstant().annotatedWith(Names.named("MAX_ACTIVE_USER")).to(1000);
    }
}
