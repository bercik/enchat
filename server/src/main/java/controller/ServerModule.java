package controller;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by tochur on 13.05.15.
 */
public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {

        /* CONSTANTS */
        bindConstant().annotatedWith(Names.named("PORT_NUMBER")).to(50000);
    }
}
