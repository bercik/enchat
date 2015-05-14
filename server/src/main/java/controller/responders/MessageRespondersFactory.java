package controller.responders;

import messages.MessageId;

/**
 * Created by tochur on 14.05.15.
 */
public class MessageRespondersFactory {
    public IMessageResponder create(MessageId messageId){
        switch (messageId){
            case LOG_IN:
                return new ExampleResponder();
        }
        return null;
    }
}
