package server.sender;

import com.google.inject.Singleton;
import message.types.EncryptedMessage;
import message.types.Pack;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by tochur on 16.05.15.
 *
 * Util for sending encrypted message to specified DataOutputStream
 */
@Singleton
public class Emitter {

    public void emit(DataOutputStream out, EncryptedMessage encrypted) throws IOException {
        out.writeInt(encrypted.getId().getIntRepresentation());
        out.writeInt(encrypted.getErrorId().getIntRepresentation());
        out.writeInt(encrypted.getPackageAmount());

        if (encrypted.getPackageAmount() > 0){
            List<Pack> packs = encrypted.getPackages();
            for (Pack pack: packs){
                out.writeInt(pack.getDataArrayLength());
                out.write(pack.getDataArray());
                out.writeInt(pack.getSignArrayLength());
                out.write(pack.getSignArray());
            }
        }
    }
}
