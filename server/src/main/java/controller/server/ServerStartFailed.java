package controller.server;

/**
 * Created by tochur on 13.05.15.
 */
public class ServerStartFailed extends Exception{
    public ServerStartFailed(String message){
        super(message);
    }
}
