package newServer.listeners.message;

import message.exceptions.MessageIdException;
import message.types.EncryptedMessage;
import message.types.Pack;
import message.utils.MessageCreator;
import messages.IncorrectMessageId;
import model.ClientInput;
import user.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Reads the message, decrypts it, and starts new (suitable) thread,
 * that is responsible for reacting for that message
 *
 * Created by tochur on 25.04.15.
 */
public class MessageReader {
    private int id;
    private int errorId;
    private int packageAmount;
    private ArrayList<Pack> packs = new ArrayList<Pack>();
    EncryptedMessage encryptedMessage;

    /**
     * Make if more safety (maybe check weather next bytes available?)
     */
    public EncryptedMessage readFromStream(DataInputStream in) throws IOException, MessageIdException {

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
}
