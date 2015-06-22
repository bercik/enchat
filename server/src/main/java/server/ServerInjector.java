package server;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Created by tochur on 13.05.15.
 */
public class ServerInjector {
    private static Injector instance = Guice.createInjector(new ServerModule());

    public static void setInjector(Injector injector){
        instance = injector;
    }

    public static Injector get(){
        return instance;
    }

}
