package server.listeners;

import controller.responders.NewClientHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class NewClientListenerTest {
    static ServerSocket serverSocket;
    static NewClientHandler clientHandler;
    static NewClientListener newClientListener;
    static SimpleClient client;
    private Thread thread;

    @BeforeClass
    public static void init() throws IOException, ServerStartFailed {
        clientHandler = mock(NewClientHandler.class);
        doNothing().when(clientHandler).run();
        doNothing().when(clientHandler).setParameters(any(Socket.class));
        newClientListener = new NewClientListener(new ServerSocket(10000), clientHandler);
        new Thread(newClientListener).start();
        client = new SimpleClient();
    }

    @Before
    public void initMethod() throws ServerStartFailed, IOException {

    }

   /* @After
    public void stopServer() throws IOException {
        newClientListener.stopListen();
        SimpleClient simpleClient = new SimpleClient();
        simpleClient.connect("localhost", 10000);
    }*/

    @Test
    public void stopping_server_works() throws Exception {
        System.out.print("TEST");
        client.connect("localhost", 10000);
        /*for(int i = 0; i<10; i++){
            new SimpleClient().connect("localhost", 10000);
        }*/
    }

   /* @Test
    public void accepts_client_request() throws Exception {

        client.connect("localhost", 10000);

        verify(clientHandler, times(1)).setParameters(any(Socket.class));
    }

    @Test
    public void accepts_client_request2() throws Exception {

        client.connect("localhost", 10000);
        client.connect("localhost", 10000);

        verify(clientHandler, times(2)).setParameters(any(Socket.class));
    }*/

    /*@Test()
    public void creating_new_client_on_the_same_socket() throws Exception {
        SimpleClient client = new SimpleClient();


    }*/

  /*  @Test(expected = BindException.class)
    public void creating_new_client_on_the_same_socket() throws Exception {
        new Thread(new NewClientListener(new ServerSocket(10000), clientHandler)).start();
        new Thread(new NewClientListener(new ServerSocket(10000), clientHandler)).start();
    }

    @Test
    public void injector_every_time_creates_the_same_listener() throws Exception {
        Injector injector = Guice.createInjector(new ServerModule());
        NewClientListener first = injector.getInstance(NewClientListener.class);
        NewClientListener second = injector.getInstance(NewClientListener.class);

        System.out.print("First: " + first);
        System.out.print("Second: " + second);
        assertTrue(first.equals(second));
    }*/
//////////////////////////////////////////////////////
  /*  @Test
    public void different_injectors_instances_returns_the_new_instances_of_listeners() throws Exception {
        Injector injector = Guice.createInjector(new ServerModule());
       // Injector injector2 = Guice.createInjector(new ServerModule());
        //NewClientListener first = injector.getInstance(NewClientListener.class);
        //NewClientListener second = injector2.getInstance(NewClientListener.class);

    }

    @Test
    public void stop_listeners_ends_thread_when() throws Exception {
        Injector injector = Guice.createInjector(new ServerModule());
        // Injector injector2 = Guice.createInjector(new ServerModule());
        //NewClientListener first = injector.getInstance(NewClientListener.class);
        //NewClientListener second = injector2.getInstance(NewClientListener.class);

    }*/
   /* @Test
    public void accepts_client_request() throws Exception {
        clientHandler = mock(NewClientHandler.class);
        new Thread(new NewClientListener(serverSocket, clientHandler)).start();

        SimpleClient client = new SimpleClient();
        client.connect("localhost", 10000);

        verify(clientHandler, times(1)).setParameters(any(Socket.class));
    }

    @Test
    public void accepts_client_request2() throws Exception {
        clientHandler = mock(NewClientHandler.class);
        new Thread(new NewClientListener(serverSocket, clientHandler)).start();

        SimpleClient client = new SimpleClient();
        client.connect("localhost", 10000);

        verify(clientHandler, times(1)).setParameters(any(Socket.class));
    }*/

   /* @Test
    public void accepts_many_clients_request() throws Exception {

        SimpleClient client = new SimpleClient();
        SimpleClient client2 = new SimpleClient();
        client.connect("localhost", 10000);
        client2.connect("localhost", 10000);
        for(int i = 0; i<5; i++){
            client.connect("localhost", 10000);
        }

        verify(clientHandler, times(2)).setParameters(any(Socket.class));

    }*/
}