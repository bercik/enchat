package server.sender;

import com.google.inject.Injector;
import server.ServerInjector;

/**
 * Created by tochur on 17.05.15.
 */
public class ServerInjectorWrapper {

    public Injector getServerInjector(){
        return ServerInjector.get();
    }
}
