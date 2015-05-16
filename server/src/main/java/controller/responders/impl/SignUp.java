package controller.responders.impl;

import com.google.inject.Inject;
import controller.responders.IMessageResponder;
import controller.utils.cypher.Decryption;
import controller.utils.verifiers.state.StateVerifier;
import message.exceptions.UnableToSendMessage;
import message.generators.Sign_Up;
import message.types.UEMessage;
import message.types.UMessage;
import model.containers.permanent.Registration;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import newServer.sender.MessageSender;
import rsa.exceptions.EncryptionException;

import java.io.IOException;

/**
 * Created by tochur on 15.05.15.
 */
public class SignUp implements IMessageResponder {
    private Decryption decryption;
    private Sign_Up sign_up;
    private StateVerifier stateVerifier;
    private Registration registration;
    private MessageSender messageSender;
    private UEMessage ueMessage;

    private Integer authorID;
    private String nick;
    private String password;
    private UMessage message;

    @Inject
    public SignUp(Decryption decryption, StateVerifier stateVerifier, Registration registration, MessageSender messageSender, Sign_Up messages){
        this.decryption = decryption;
        this.stateVerifier = stateVerifier;
        this.registration = registration;
        this.messageSender = messageSender;
        this.sign_up = messages;
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
            UEMessage answer = sign_up.ok(authorID);
            messageSender.send(answer);
        }catch (EncryptionException e){

        }catch (AlreadyInCollection e){

        }catch (UnableToSendMessage e){

        } catch (OverloadedCannotAddNew e) {

        } catch (IOException e) {

        }

    }

    private void readInfo(){
        authorID = message.getAuthorID();
        String[] strings = message.getPackages().toArray(new String[0]);
        this.nick = strings[0];
        this.password = strings[1];
    }
}
