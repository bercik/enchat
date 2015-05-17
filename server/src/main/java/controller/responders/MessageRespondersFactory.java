package controller.responders;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import controller.responders.impl.LogIn;
import controller.responders.impl.SignUp;
import messages.MessageId;
import newServer.ServerInjector;
import newServer.sender.ServerInjectorWrapper;

/**
 * Created by tochur on 14.05.15.
 */
@Singleton
public class MessageRespondersFactory {

    public IMessageResponder create(MessageId messageId, Injector injector){

        switch (messageId){
            case LOG_IN:
                return injector.getInstance(LogIn.class);
            case SIGN_UP:
                return injector.getInstance(SignUp.class);
        }
        return null;
    }


    private SignUp signUp;
    private LogIn logIn;
}
