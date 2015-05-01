package rsa.exceptions;

/**
 * Created by tochur on 01.05.15.
 */
public class EncryptionException extends Exception {
    EncryptionException(String info){
        super(info);
    }
    EncryptionException(){}
}
