package rsa.exceptions;

/**
 * Created by tochur on 01.05.15.
 */
public class EncryptionException extends Exception {
    public EncryptionException(String info){
        super(info);
    }
    public EncryptionException(){}
}
