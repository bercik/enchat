package server.listeners;

/**
 * When server was not able to start (any reason).
 *
 * @author Created by tochur on 13.05.15.
 */
public class ServerStartFailed extends Exception{
    public ServerStartFailed(String message){
        super(message);
    }
}
