package message.utils;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Message;
import messages.IncorrectMessageId;
import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 01.05.15.
 *
 * This class let to CONCRETE Messages in really convenient and safety way.
 * It is recommended for creating really new messages.
 * If you want to create Message Object from stream ex. you shall use MessageCreator instead.
 */
public class Messages {

    public static Message createInfoMessage(String message){
        List<String> strings = new LinkedList<String>();
        strings.add(message);
        return new message.types.Message(MessageId.SERVER_INFO_MESSAGE, MessageId.ErrorId.OK , 1, strings);
    }

    /*Will be removed*/
    public static message.types.Message createHeaderMessage(MessageId messageId, int errorId){
        return  new message.types.Message(MessageId.SERVER_INFO_MESSAGE, MessageId.ErrorId.OK, 0, null);
    }

    public static Message unableToDeliver(String receiverName){
        MessageId id = MessageId.CLIENT_MESSAGE;
        MessageId.ErrorId errorId = null;
        try {
            errorId = id.createErrorId(1);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new Message(id, errorId, receiverName);
    }

    public static Message loginConfirmation(String info) {
        MessageId id = MessageId.LOG_IN;
        MessageId.ErrorId error = null;
        try{
            error = id.createErrorId(0);
        }catch (IncorrectMessageId e){
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new Message(id, error, info);
    }

    public static Message wrongNickOrPassword() {
        MessageId id = MessageId.LOG_IN;
        MessageId.ErrorId error = null;
        try{
            error = id.createErrorId(1);
        }catch (IncorrectMessageId e){
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new Message(id, error);
    }

    public static Message toMuchUsersLogged() {
        MessageId id = MessageId.LOG_IN;
        MessageId.ErrorId error = null;
        try{
            error = id.createErrorId(2);
        }catch (IncorrectMessageId e){
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new Message(id, error);
    }

    public static Message occupiedLogin() {
        MessageId id = MessageId.SIGN_UP;
        MessageId.ErrorId errorId = null;
        try {
            errorId = id.createErrorId(1);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new Message(id, errorId);
    }

    public static EncryptedMessage loggedSuccessfully(){
        MessageId id = MessageId.SIGN_UP;
        MessageId.ErrorId errorId = null;
        try{
            errorId = id.createErrorId(0);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage( new Header(id, errorId, 0) );
    }
}
