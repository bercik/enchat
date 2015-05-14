package newServer.listeners.message;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.DataInputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.*;

public class InputStreamsHandlerTest {
    private static StreamsHandler streamsHandler;
    private InputStreamsHandler inputStreamsHandler;

    //Array Of mocked DataInputStream
    private static int pairsAmount = 5;
    private static DataInputStream[] inputStreams = new DataInputStream[pairsAmount];
    //Array Of id's
    private Integer[] IDs = new Integer[] { 1235, 71, 26 , 2, 91};


    @BeforeClass
    public static void init(){
        for(int i = 0; i < inputStreams.length; i++){
            inputStreams[i] = mock(DataInputStream.class);
        }
        streamsHandler = mock(StreamsHandler.class);
    }

    @Before
    public void initMethod(){
        this.inputStreamsHandler = new InputStreamsHandler( streamsHandler );
    }

    @Test
    public void add_stream_to_scan_add_new_stream_after_update() throws Exception {
        inputStreamsHandler.addStreamToScan(IDs[0], inputStreams[0]);
        inputStreamsHandler.updateClientsInputs();

        assertThat(inputStreamsHandler.getScannedStreamsAmount(), is(1));
    }

    @Test
    public void inserting_and_delete_inputs_works() throws Exception {
        for (int i = 0; i<pairsAmount; i++){
            inputStreamsHandler.addStreamToScan(IDs[i], inputStreams[i]);
        }

        inputStreamsHandler.updateClientsInputs();
        assertThat(inputStreamsHandler.getScannedStreamsAmount(), is(5));

        inputStreamsHandler.deleteInputWithId(71);
        inputStreamsHandler.deleteInputWithId(1235);
        inputStreamsHandler.addStreamToScan(71, inputStreams[0]);
        inputStreamsHandler.deleteInputWithId(71);

        inputStreamsHandler.updateClientsInputs();
        assertThat(inputStreamsHandler.getScannedStreamsAmount(), is(3));

    }

    @Test
    public void update_Clients_Inputs_Cleans_Buffers() throws Exception {
        inputStreamsHandler.addStreamToScan(IDs[0], inputStreams[0]);
        inputStreamsHandler.updateClientsInputs();
        assertThat(inputStreamsHandler.getScannedStreamsAmount(), is(1));
        inputStreamsHandler.updateClientsInputs();
        assertThat(inputStreamsHandler.getScannedStreamsAmount(), is(1));
    }
}