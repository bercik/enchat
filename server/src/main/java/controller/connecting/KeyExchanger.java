package controller.connecting;

import com.google.inject.Inject;
import controller.utils.cypher.EncryptionUtil;
import model.user.Users;

/**
 * Created by tochur on 15.05.15.
 */
public class KeyExchanger {

    @Inject
    public KeyExchanger(EncryptionUtil encryption, Users users){

    }
}
