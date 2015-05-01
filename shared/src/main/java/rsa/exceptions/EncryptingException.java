package rsa.exceptions;

/**
 * Created by tochur on 01.05.15.
 */
public class EncryptingException extends EncryptionException {
    public EncryptingException(String reason){
        super(reason);
    }
    public EncryptingException(){}
}
