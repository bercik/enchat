/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.responders.impl;

import controller.utils.cypher.Decryption;
import controller.utils.cypher.DecryptionUtil;
import controller.utils.cypher.EncryptionUtil;
import controller.utils.state.StateManager;
import message.generators.Sign_Up;
import message.types.Header;
import message.types.Message;
import message.types.UEMessage;
import message.types.UMessage;
import messages.MessageId;
import model.Account;
import model.containers.permanent.Accounts;
import model.containers.permanent.Authentication;
import model.containers.permanent.Registration;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import model.containers.temporary.UserStates;
import model.user.UserState;
import model.user.Verifier;
import org.junit.*;
import rsa.KeyContainer;
import rsa.PrivateKeyInfo;
import rsa.PublicKeyInfo;
import server.sender.Emitter;
import server.sender.MessageSender;
import server.sender.OutStreams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author gregory
 */
public class SignUpIT {
    
    //Registered previously
    private static Integer user1ID = 5;
    private static String user1Nick = "LOGIN1";
    private static String user1Pass = "PASS1";
    
    
    //Try to register
    private static Integer user2ID = 6;
    private static String user2Nick = "LOGIN2";
    private static String user2Pass = "PASS2";
    
    //Busy login
    private static Integer user3ID = 7;
    private static String user3Nick = "LOGIN2";
    private static String user3Pass = "PASS33";
    
    //Bad login
    private static String user2BadNick = "asd,.5%^0.1";
    
    //Bad password
    private static String user2BadPass = "dsa@#*&^lAJKLKjh boo,.'p';'.";
    
    
    private static DecryptionUtil decryptionUtil;
    private static PublicKeysManager publicKeysManager;
    
    private static PublicKeyInfo serverPublicKeyInfo;
    private static PrivateKeyInfo serverPrivateKeyInfo;
    
    private static PrivateKeyInfo clientPrivateKeyInfo;
    private static PublicKeyInfo clientPublicKeyInfo;
    
    private static Decryption decryption;
    
    private static UserStates userStates;
    
    private static Verifier verifier;
    private static StateManager stateManager;
    
    private static Accounts accounts;
    private static Authentication authentication;
    
    public SignUpIT() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        
        //Initialize keys
        KeyContainer serverKeyContainer = new KeyContainer();
        serverPublicKeyInfo = serverKeyContainer.getPublicKeyInfo();
        serverPrivateKeyInfo = serverKeyContainer.getPrivateKeyInfo();

        KeyContainer clientKeyContainer = new KeyContainer();
        clientPrivateKeyInfo = clientKeyContainer.getPrivateKeyInfo();
        clientPublicKeyInfo = clientKeyContainer.getPublicKeyInfo();
        
        decryptionUtil = new DecryptionUtil(serverPrivateKeyInfo.getPrivateKey());
        publicKeysManager = new PublicKeysManager(new PublicKeys());
        
        //Adding public key to collection.
        publicKeysManager.addKey(user2ID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());
        
        
        //Initialize Decription
        decryption = new Decryption(decryptionUtil, publicKeysManager);
        

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //Initialize StateManager
        userStates = new UserStates();
        userStates.updateState(user1ID, UserState.DISCONNECTED);
        userStates.updateState(user2ID, UserState.CONNECTED_TO_SERVER);
        
        //Initialize StateManager        
        verifier = new Verifier();
        stateManager = new StateManager(userStates, verifier);
        
        //Initialize authentication
        accounts = new Accounts(5);
        accounts.addAccount(user1Nick, new Account(user1Nick,user1Pass));
        authentication = new Authentication(accounts.getMap());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of serveEvent method, of class SignUp.
     */
    @Test
    public void testSignUp_normal() throws Exception  {
       System.out.println("testSignUp_normal");
        
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {user2Nick, user2Pass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(user2ID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());
        

        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(user2ID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize Log_In
        Sign_Up messages = new Sign_Up();
        
        //Initialize LoggedUtil
        Registration registration = new Registration(accounts);
        
        SignUp signUp = new SignUp(decryption, stateManager, messageSender,registration, messages);
        
        signUp.serveEvent(ueMessage);
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals(MessageId.SIGN_UP, MessageId.createMessageId(messageID));
        assertEquals(MessageId.SIGN_UP.createErrorId(0),MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.CONNECTED_TO_SERVER, userStates.getUserState(user2ID));
        assertEquals(true,accounts.containKey(user2Nick));
    }

    @Test
    public void testSignUp_busyLogin() throws Exception  {
       System.out.println("testSignUp_busyLogin");

        //Adding public key to collection.
        publicKeysManager.addKey(user2ID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());

        Decryption decryption = new Decryption(decryptionUtil, publicKeysManager);
        
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {user2Nick, user2Pass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(user2ID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());

        
        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(user2ID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Add user
        accounts.addAccount(user3Nick, new Account(user3Nick,user3Pass));
        authentication = new Authentication(accounts.getMap());
        
        //Initialize Log_In
        Sign_Up messages = new Sign_Up();
        
        //Initialize LoggedUtil
        Registration registration = new Registration(accounts);
        
        SignUp signUp = new SignUp(decryption, stateManager, messageSender,registration, messages);
        
        signUp.serveEvent(ueMessage);
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals(MessageId.SIGN_UP, MessageId.createMessageId(messageID));
        assertEquals(MessageId.SIGN_UP.createErrorId(1),MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.CONNECTED_TO_SERVER, userStates.getUserState(user2ID));
        assertEquals(true,accounts.containKey(user2Nick));
    }
    
    @Test
    public void testSignUp_tooManyAccounts() throws Exception  {
       System.out.println("testSignUp_tooManyAccounts");

      
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {user2Nick, user2Pass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(user2ID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());

        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(user2ID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize authentication with 1 user limit
        accounts = new Accounts(1);
        accounts.addAccount(user1Nick, new Account(user1Nick,user1Pass));
        authentication = new Authentication(accounts.getMap());
        
        //Initialize Log_In
        Sign_Up messages = new Sign_Up();
        
        //Initialize LoggedUtil
        Registration registration = new Registration(accounts);
        
        SignUp signUp = new SignUp(decryption, stateManager, messageSender,registration, messages);
        
        signUp.serveEvent(ueMessage);
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals(MessageId.SIGN_UP, MessageId.createMessageId(messageID));
        assertEquals(MessageId.SIGN_UP.createErrorId(4),MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.CONNECTED_TO_SERVER, userStates.getUserState(user2ID));
        assertEquals(false,accounts.containKey(user2Nick));
    }


    /*
    Commented by tochur
    Server was not responsible for validation the syntactical correctness or login and password.
    Client weather login and password fulfill all needments.
    Consider that when you throw this responsibility for client, you receive additional flexibility,
    you can compile a few clients version with different requirements about nick and password and all of them
    can connect to one server.

    */
    /*@Test
    public void testSignUp_badLogin() throws Exception  {
       System.out.println("testSignUp_badLogin");

        
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {user2BadNick, user2Pass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(user2ID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());

        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(user2ID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize Log_In
        Sign_Up messages = new Sign_Up();
        
        //Initialize LoggedUtil
        Registration registration = new Registration(accounts);
        
        SignUp signUp = new SignUp(decryption, stateManager, messageSender,registration, messages);
        
        signUp.serveEvent(ueMessage);
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals(MessageId.SIGN_UP, MessageId.createMessageId(messageID));
        assertEquals(MessageId.SIGN_UP.createErrorId(2),MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.CONNECTED_TO_SERVER, userStates.getUserState(user2ID));
        assertEquals(false,accounts.containKey(user2Nick));
    }
    
    @Test
    public void testSignUp_badPassword() throws Exception  {
       System.out.println("testSignUp_badPassword");

        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {user2Nick, user2BadPass};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(user2ID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());

        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(user2ID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize Log_In
        Sign_Up messages = new Sign_Up();
        
        //Initialize LoggedUtil
        Registration registration = new Registration(accounts);
        
        SignUp signUp = new SignUp(decryption, stateManager, messageSender,registration, messages);
        
        signUp.serveEvent(ueMessage);
        Thread.sleep(1000);

  
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        

        assertEquals(MessageId.SIGN_UP, MessageId.createMessageId(messageID));
        assertEquals(MessageId.SIGN_UP.createErrorId(3),MessageId.createMessageId(messageID).createErrorId(errorID));
        assertEquals(UserState.CONNECTED_TO_SERVER, userStates.getUserState(user2ID));
        assertEquals(false,accounts.containKey(user2Nick));
    }*/
    
}
