package server;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Class that holds Injector, to inject some objects.
 *
 * @author Created by tochur on 13.05.15.
 */
public class ServerInjector {
    private static Injector instance = Guice.createInjector(new ServerModule());

    /**
     * Sets the Injector
     * @param injector Injector, injector object.
     */
    public static void setInjector(Injector injector){
        instance = injector;
    }

    /**
     * Gets the Injector object.
     * @return Injector object.
     */
    public static Injector get(){
        return instance;
    }

}
