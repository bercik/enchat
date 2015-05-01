package rsa.exceptions;

/**
 * Created by tochur on 01.05.15.
 */
public class DecryptingException extends EncryptionException{
    public DecryptingException(String info){
        super(info);
    }
    public DecryptingException(){}
}
