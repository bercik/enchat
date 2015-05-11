package message;

import message.exceptions.MessageIdException;
import message.types.EncryptedMessage;
import message.utils.MessageCreator;
import message.types.Pack;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by tochur on 17.04.15 modified 10,05,15 to more general
 *
 * It is responsible for reading the message from the stream.
 * Message from buffer is changed to EncryptedMessage object.
 * The message is read from the clients buffer.
 * Message format is:
 *      id      - (int32)
 *      errorId  - (int32)
 *      packageAmount - (int32)
 *      dataArrayLength - (int32)
 *      dataArray       - ( byte[byteArrayLength] )
 *      signArrayLength - (int32)
 *      signArray       - ( byte[signArrayLength] )
 */
public class MessageReader {
    //The stream which from witch messages are read.
    private DataInputStream in;
    //Temporary - parameters data for message.
    private int id;
    private int errorId;
    private int packageAmount;
    private ArrayList<message.types.Pack> packs = new ArrayList<message.types.Pack>();

    public MessageReader(DataInputStream in){
        this.in = in;
    }

    /**
     * Reads the message from actually set stream
     * @return Message read from stream
     * @throws IOException - when Sth goes wrong with reading
     * @throws MessageIdException - when Header of Message is incorrect, (unknown message Type)
     */
    public EncryptedMessage readMessage() throws IOException, MessageIdException {
        //Reading message header
        id = in.readInt();
        errorId = in.readInt();
        packageAmount = in.readInt();

        //Reading encrypted packages if exists
        System.out.print("Package Amount: " + packageAmount);
        if(packageAmount > 0){
            for(int i = 0; i < packageAmount; i++){
                System.out.print("Reading package " + i);
                int dataArrayLength = in.readInt();
                System.out.print("Sign Array Length: " + dataArrayLength);
                byte[] dataArray = new byte[dataArrayLength];
                in.readFully(dataArray);

                int signArrayLength = in.readInt();
                System.out.print("Sign Array Length: " + signArrayLength);
                byte[] signArray = new byte[signArrayLength];
                in.readFully(signArray);

                Pack pack = new Pack(dataArray, signArray);
                packs.add(pack);
            }
            return MessageCreator.fromStream(id, errorId, packageAmount, packs);
        }else{
            return MessageCreator.fromStream(id, errorId);
        }
    }

    /**
     * Changing stream from with messages are read.
     * @param in - new stream.
     */
    public void setIn(DataInputStream in){
        this.in = in;
    }
}
