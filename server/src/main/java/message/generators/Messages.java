package message.generators;

import message.types.EncryptedMessage;

/**
 * Created by tochur on 11.05.15.
 */
public class Messages {

    public Log_In logIn(){
        return new Log_In();
    }

    public Sign_Up signUp(){
        return new Sign_Up();
    }

    public Black_List blackList(){
        return new Black_List();
    }

    public Logged_List loggedList(){
        return new Logged_List();
    }

    public Server_error serverError(){
        return new Server_error();
    }

    public Clients_Message clientsMessage(){
        return new Clients_Message();
    }
    public Conversation_Request conversationRequest(){
        return new Conversation_Request();
    }
    public Conversationalist_Disconnected conversationalistDisconnected(){
        return new Conversationalist_Disconnected();
    }

    public Incoming_Conversation incomingConversation(){
        return new Incoming_Conversation();
    }

    public Junk junk(){
        return new Junk();
    }
}
