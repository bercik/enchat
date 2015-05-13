package messages;

/**
 * Created by tochur on 17.04.15.
 */
public class IncorrectMessageId extends RuntimeException {
    IncorrectMessageId(String message){
        super(message);
    }
}
