package messages;

/**
 * Created by tochur on 17.04.15.
 */
public class IncorrectErrorId extends RuntimeException {
    IncorrectErrorId(String message){
        super(message);
    }
}
