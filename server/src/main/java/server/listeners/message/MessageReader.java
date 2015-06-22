package server.listeners.message;

import message.exceptions.MessageIdException;
import message.types.EncryptedMessage;
import message.types.Pack;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * It is responsible for reading the message from the stream.
 * Message from buffer is changed to EncryptedMessage object.
 * The message is read from the DataInputStream.
 * Message format is:
 *      id      - (int32)
 *      errorId  - (int32)
 *      packageAmount - (int32)
 *      dataArrayLength - (int32)
 *      dataArray       - ( byte[byteArrayLength] )
 *      signArrayLength - (int32)
 *      signArray       - ( byte[signArrayLength] )
 *
 * @author Created by tochur on 15.05.15.
 */
public class MessageReader {
    private int id;
    private int errorId;
    private int packageAmount;
    private EncryptedMessage encryptedMessage;


    /**
     * Reads the message from stream.
     * @param in - data input stream
     * @return - encrypted message read from stream.
     * @throws IOException - when socket is closed.
     * @throws MessageIdException - when message from buffer has incorrect format (header)
     */
    public EncryptedMessage readFromStream(DataInputStream in) throws IOException, MessageIdException {
        ArrayList<Pack> packs = new ArrayList<Pack>();
        //Reading message header
        id = in.readInt();
        errorId = in.readInt();
        packageAmount = in.readInt();

        //Reading encrypted packages if exists
        if(packageAmount > 0){
            for(int i = 0; i < packageAmount; i++){
                int dataArrayLength = in.readInt();
                byte[] dataArray = new byte[dataArrayLength];
                in.readFully(dataArray);

                int signArrayLength = in.readInt();
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
}
