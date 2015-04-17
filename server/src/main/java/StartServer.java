/**
 * Created by tochur on 13.04.15.
 */
public class StartServer {
    public static void main(String[] args){
        Server server = Server.getInstance();
        System.out.println("Starting server from main");
        server.start();
    }
}
