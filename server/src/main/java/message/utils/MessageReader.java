package message.utils;

import message.types.EncryptedMessage;
import message.types.Pack;
import messages.IncorrectMessageId;
import user.ActiveUser;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tochur on 17.04.15.
 *
 * It is responsible for reading the message from the user.
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
    private int id;
    private int errorId;
    private int packageAmount;
    private ArrayList<Pack> packs = new ArrayList<Pack>();

    private DataInputStream in;

    public EncryptedMessage readMessage(ActiveUser activeUser) throws IOException, IncorrectMessageId {
            //Reading message header
            in = activeUser.getInputStream();
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
