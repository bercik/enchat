/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.responders.impl;

import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.cypher.Decryption;
import controller.utils.cypher.DecryptionUtil;
import controller.utils.cypher.EncryptionUtil;
import controller.utils.state.StateManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import message.generators.Log_In;
import message.types.Header;
import message.types.Message;
import message.types.UEMessage;
import message.types.UMessage;
import messages.MessageId;
import model.Account;
import model.containers.permanent.Accounts;
import model.containers.permanent.Authentication;
import model.containers.temporary.Logged;
import model.containers.temporary.LoggedUtil;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import model.containers.temporary.UserStates;
import model.user.UserState;
import model.user.Verifier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rsa.KeyContainer;
import rsa.PrivateKeyInfo;
import rsa.PublicKeyInfo;
import server.sender.Emitter;
import server.sender.MessageSender;
import server.sender.OutStreams;

/**
 *
 * @author gregory
 */
public class LogInIT {
    
    private static Integer userID = 5;
    private static Integer user2ID = 6;
    private static String user1Nick = "LOGIN1";
    private static String user2Nick = "LOGIN2";
    private static String user1Pass = "PASS1";
    private static String user2Pass = "PASS2";
    private static String userWrongPass = "PASS2";

    /**
     * Test of serveEvent method, of class LogIn.
     */
    @Test
    public void testLogIn_normal() throws Exception {
        System.out.println("testLogIn_normal");
        
        //Initialize Decription
        KeyContainer serverKeyContainer = new KeyContainer();
        PublicKeyInfo serverPublicKeyInfo = serverKeyContainer.getPublicKeyInfo();
        PrivateKeyInfo serverPrivateKeyInfo = serverKeyContainer.getPrivateKeyInfo();

        KeyContainer clientKeyContainer = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo = clientKeyContainer.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo = clientKeyContainer.getPublicKeyInfo();
        
        DecryptionUtil decryptionUtil = new DecryptionUtil(serverPrivateKeyInfo.getPrivateKey());
        PublicKeysManager publicKeysManager = new PublicKeysManager(new PublicKeys());
        //Adding public key to collection.
        publicKeysManager.addKey(userID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());

        Decryption decryption = new Decryption(decryptionUtil, publicKeysManager);
        
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.LOG_IN, MessageId.LOG_IN.createErrorId(0), 2);
        String[] info = new String[] {user1Nick, user1Pass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(userID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());
        
        //Initialize StateManager
        UserStates userStates = new UserStates();
        userStates.updateState(userID, UserState.CONNECTED_TO_SERVER);
        
        Verifier verifier = new Verifier();
        StateManager stateManager = new StateManager(userStates, verifier);
        
        
        //Initialize authentication
        //Accounts accounts = new Accounts(10);
        Map<String, Account> accounts = new HashMap<>();
        accounts.put(user1Nick, new Account(user1Nick,user1Pass));
        Authentication authentication = new Authentication(accounts);
        
        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(userID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize Log_In
        Log_In messages = new Log_In();
        
        //Initialize LoggedUtil
        LoggedUtil loggedUtil = new LoggedUtil(new Logged());
        
        LogIn logIn = new LogIn(decryption, stateManager,authentication, messageSender, messages,loggedUtil);
        
        logIn.serveEvent(ueMessage);
        //logIn.run();
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals( MessageId.LOG_IN, MessageId.createMessageId(messageID));
        assertEquals(MessageId.LOG_IN.createErrorId(0),MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.LOGGED, userStates.getUserState(userID));
        assertEquals(true,loggedUtil.isLogged(userID));

    }

    @Test
    public void testLogIn_doubleLogin() throws Exception {
        System.out.println("testLogIn_doubleLogin()");
        
        //Initialize Decription
        KeyContainer serverKeyContainer = new KeyContainer();
        PublicKeyInfo serverPublicKeyInfo = serverKeyContainer.getPublicKeyInfo();
        PrivateKeyInfo serverPrivateKeyInfo = serverKeyContainer.getPrivateKeyInfo();

        KeyContainer clientKeyContainer = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo = clientKeyContainer.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo = clientKeyContainer.getPublicKeyInfo();
        
        DecryptionUtil decryptionUtil = new DecryptionUtil(serverPrivateKeyInfo.getPrivateKey());
        PublicKeysManager publicKeysManager = new PublicKeysManager(new PublicKeys());
        //Adding public key to collection.
        publicKeysManager.addKey(userID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());

        Decryption decryption = new Decryption(decryptionUtil, publicKeysManager);
        
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.LOG_IN, MessageId.LOG_IN.createErrorId(0), 2);
        String[] info = new String[] {user1Nick, user1Pass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(userID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());
        
        //Initialize StateManager
        UserStates userStates = new UserStates();
        userStates.updateState(userID, UserState.LOGGED);
        
        Verifier verifier = new Verifier();
        StateManager stateManager = new StateManager(userStates, verifier);
        
        
        //Initialize authentication
        //Accounts accounts = new Accounts(10);
        Map<String, Account> accounts = new HashMap<>();
        Account account1 = new Account(user1Nick,user1Pass);
        accounts.put(user1Nick, account1);
        Authentication authentication = new Authentication(accounts);
        
        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(userID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize Log_In
        Log_In messages = new Log_In();
        
        //Initialize LoggedUtil
        LoggedUtil loggedUtil = new LoggedUtil(new Logged());
        loggedUtil.add(userID, account1);
        
        LogIn logIn = new LogIn(decryption, stateManager,authentication, messageSender, messages,loggedUtil);
        
        logIn.serveEvent(ueMessage);
        //logIn.run();
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals(MessageId.LOG_IN, MessageId.createMessageId(messageID));
        assertEquals(MessageId.LOG_IN.createErrorId(3), MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.LOGGED,userStates.getUserState(userID));
        assertEquals(true,loggedUtil.isLogged(userID));
        
  

    }
    
    @Test
    public void testLogIn_wrongPass() throws Exception {
        System.out.println("testLogIn_wrongPass");
        
        //Initialize Decription
        KeyContainer serverKeyContainer = new KeyContainer();
        PublicKeyInfo serverPublicKeyInfo = serverKeyContainer.getPublicKeyInfo();
        PrivateKeyInfo serverPrivateKeyInfo = serverKeyContainer.getPrivateKeyInfo();

        KeyContainer clientKeyContainer = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo = clientKeyContainer.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo = clientKeyContainer.getPublicKeyInfo();
        
        DecryptionUtil decryptionUtil = new DecryptionUtil(serverPrivateKeyInfo.getPrivateKey());
        PublicKeysManager publicKeysManager = new PublicKeysManager(new PublicKeys());
        //Adding public key to collection.
        publicKeysManager.addKey(userID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());

        Decryption decryption = new Decryption(decryptionUtil, publicKeysManager);
        
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.LOG_IN, MessageId.LOG_IN.createErrorId(0), 2);
        String[] info = new String[] {user1Nick, userWrongPass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(userID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());
        
        //Initialize StateManager
        UserStates userStates = new UserStates();
        userStates.updateState(userID, UserState.CONNECTED_TO_SERVER);
        
        Verifier verifier = new Verifier();
        StateManager stateManager = new StateManager(userStates, verifier);
        
        
        //Initialize authentication
        //Accounts accounts = new Accounts(10);
        Map<String, Account> accounts = new HashMap<>();
        accounts.put(user1Nick, new Account(user1Nick,user1Pass));
        Authentication authentication = new Authentication(accounts);
        
        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(userID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize Log_In
        Log_In messages = new Log_In();
        
        //Initialize LoggedUtil
        LoggedUtil loggedUtil = new LoggedUtil(new Logged());
        
        LogIn logIn = new LogIn(decryption, stateManager,authentication, messageSender, messages,loggedUtil);
        
        logIn.serveEvent(ueMessage);
        //logIn.run();
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals(MessageId.LOG_IN, MessageId.createMessageId(messageID));
        assertEquals(MessageId.LOG_IN.createErrorId(1), MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.CONNECTED_TO_SERVER,userStates.getUserState(userID));
        assertEquals(false,loggedUtil.isLogged(userID));
        
  

    }
    
    
}