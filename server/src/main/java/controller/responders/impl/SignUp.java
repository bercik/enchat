package controller.responders.impl;

import com.google.inject.Inject;
import containers.ActiveUsers;
import controller.responders.IMessageResponder;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.cypher.Decryption;
import controller.utils.verifiers.state.StateVerifier;
import message.exceptions.UnableToSendMessage;
import message.types.EncryptedMessage;
import message.types.UEMessage;
import message.types.UMessage;
import model.containers.permanent.Registration;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import newServer.sender.MessageSender;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;

import java.io.IOException;

/**
 * Created by tochur on 15.05.15.
 */
public class SignUp implements IMessageResponder {
    private Decryption decryption;
    private StateVerifier stateVerifier;
    private Registration registration;
    private MessageSender messageSender;
    private UEMessage ueMessage;
    private ActiveUsers activeUsers;

    private Integer ID;
    private String nick;
    private String password;
    private UMessage message;

    @Inject
    public SignUp(Decryption decryption, StateVerifier stateVerifier, Registration registration, MessageSender messageSender, ActiveUsers activeUsers){
        this.decryption = decryption;
        this.stateVerifier = stateVerifier;
        this.registration = registration;
        this.messageSender = messageSender;
    }

    @Override
    public void serveEvent(UEMessage ueMessage) {
        this.ueMessage = ueMessage;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try{
            stateVerifier.verify(ueMessage);
            message = decryption.decryptMessage(ueMessage);
            readInfo();
            registration.register(nick, password);
            EncryptedMessage answer = encryption.encrypt(Message message, PublicKey senderKey);
            messageSender.send(answer,Integer ID);
        }catch (EncryptionException e){

        }catch (IncorrectUserStateException e){

        }catch (AlreadyInCollection e){

        }catch (DecryptingException e){

        }catch (UnableToSendMessage e){

        } catch (OverloadedCannotAddNew e) {

        } catch (IOException e) {

        }

    }

    private void readInfo(){
        ID = message.get
        String[] strings = message.getPackages().toArray(new String[0]);
        this.nick = strings[0];
        this.password = strings[1];
    }
}
