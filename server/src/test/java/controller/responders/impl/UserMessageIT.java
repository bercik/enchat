/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.responders.impl;

import controller.utils.cypher.Decryption;
import controller.utils.cypher.DecryptionUtil;
import controller.utils.cypher.Encryption;
import controller.utils.cypher.EncryptionUtil;
import controller.utils.state.StateManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import message.generators.Clients_Message;
import message.generators.Conversation_Request;
import message.generators.Incoming_Conversation;
import message.generators.KeyPackageSupplier;
import message.generators.Log_In;
import message.generators.Server_Message;
import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Message;
import message.types.UEMessage;
import message.types.UMessage;
import messages.MessageId;
import model.Account;
import model.ChatRoom;
import model.containers.permanent.Accounts;
import model.containers.permanent.Authentication;
import model.containers.permanent.blacklist.BlackListAccessor;
import model.containers.permanent.blacklist.BlackListUtil;
import model.containers.temporary.Logged;
import model.containers.temporary.LoggedUtil;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import model.containers.temporary.RoomManager;
import model.containers.temporary.Rooms;
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
import server.listeners.message.MessageReader;
import server.sender.Emitter;
import server.sender.MessageSender;
import server.sender.OutStreams;

/**
 *
 * @author gregory
 */
public class UserMessageIT {
        //We
    private static Integer userID = 5;
    private static String user1Nick = "LOGIN1";
    private static String user1Pass = "PASS1";
    
    //The user we want to talk
    private static Integer user2ID = 6;
    private static String user2Nick = "LOGIN2";
    private static String user2Pass = "PASS2";
    

    
    public UserMessageIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown(){
    }
    
    @Test
    public void testUserMessage_normal() throws Exception {
        System.out.println("testUserMessage_normal");
    
        //Initialize Decription
        KeyContainer serverKeyContainer = new KeyContainer();
        PublicKeyInfo serverPublicKeyInfo = serverKeyContainer.getPublicKeyInfo();
        PrivateKeyInfo serverPrivateKeyInfo = serverKeyContainer.getPrivateKeyInfo();

        KeyContainer clientKeyContainer = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo = clientKeyContainer.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo = clientKeyContainer.getPublicKeyInfo();
        
        KeyContainer clientKeyContainer2 = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo2 = clientKeyContainer2.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo2 = clientKeyContainer2.getPublicKeyInfo();
        
        DecryptionUtil decryptionUtil = new DecryptionUtil(serverPrivateKeyInfo.getPrivateKey());
        PublicKeys publicKeys = new PublicKeys();
        PublicKeysManager publicKeysManager = new PublicKeysManager(publicKeys);
        //Adding public key to collection.
        publicKeysManager.addKey(userID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());
        publicKeysManager.addKey(user2ID, clientPublicKeyInfo2.getPublicKey(), clientPrivateKeyInfo2.getModulus(), clientPrivateKeyInfo2.getExponent());

        Decryption decryption = new Decryption(decryptionUtil, publicKeysManager);
        
        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.CLIENT_MESSAGE, MessageId.CLIENT_MESSAGE.createErrorId(0), 1);
        Message message = new Message(header, "Message12345");
        UMessage uMessage = new UMessage(userID, message);


        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());
        
        //Initialize StateManager
        UserStates userStates = new UserStates();
        userStates.updateState(userID, UserState.IN_ROOM);
        userStates.updateState(user2ID, UserState.IN_ROOM);
        
        Verifier verifier = new Verifier();
        StateManager stateManager = new StateManager(userStates, verifier);
        
        
        //Initialize authentication
        //Accounts accounts = new Accounts(10);
        Accounts accounts = new Accounts(5);
        Account account1 = new Account(user1Nick,user1Pass);
        Account account2 = new Account(user2Nick,user2Pass);
        accounts.addAccount(user1Nick, account1);
        accounts.addAccount(user2Nick, account2);
        Authentication authentication = new Authentication(accounts.getMap());

        
        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(userID, new DataOutputStream(baos));
        

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        outStreams.addStream(user2ID, new DataOutputStream(baos2));
        
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        
        //Initialize Server_Message
        Server_Message serverMessage = new Server_Message();
        
        //Initialize Clients_Message
        Clients_Message clientsMessage = new Clients_Message();
        
      
        
        //Initialize rooms
        Rooms rooms = new Rooms();
        ChatRoom chatRoom = new ChatRoom(userID,user2ID);
        rooms.addNew(user2ID, chatRoom);
        rooms.addNew(userID, chatRoom);
        RoomManager roomManager = new RoomManager(rooms);
  
        UserMessage userMessage = new UserMessage(stateManager, messageSender, roomManager, serverMessage, clientsMessage);
        userMessage.serveEvent(ueMessage);
        //logIn.run();
        Thread.sleep(1000);

        
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int messageID = dis.readInt();
        int errorID = dis.readInt();
        
        
        //Read message
        DataInputStream dis2 = new DataInputStream(new ByteArrayInputStream(baos2.toByteArray()));
        MessageReader messageReader = new MessageReader();
        EncryptedMessage encryptedMessage = messageReader.readFromStream(dis2);
        UMessage receivedUMessage = decryption.decryptMessage(ueMessage);
        System.out.println("Message " + receivedUMessage.getPackages().get(0));
        
        
        assertEquals(true, receivedUMessage.getPackages().get(0).equals("Message12345"));
        assertEquals(MessageId.CLIENT_MESSAGE, MessageId.createMessageId(messageID));
        assertEquals(MessageId.CLIENT_MESSAGE.createErrorId(0), MessageId.createMessageId(messageID).createErrorId(errorID)); 
    }
}
