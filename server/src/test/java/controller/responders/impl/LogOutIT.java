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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import message.generators.Log_In;
import message.generators.Log_Out;
import message.types.Header;
import message.types.Message;
import message.types.UEMessage;
import message.types.UMessage;
import messages.MessageId;
import model.Account;
import model.containers.permanent.Authentication;
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
import model.ChatRoom;
import model.containers.permanent.Accounts;
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
public class LogOutIT {
    private static Integer userID = 5;
    private static Integer user2ID = 6;
    private static String user1Nick = "LOGIN1";
    private static String user1Pass = "PASS1";
    
    public LogOutIT() {
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
    public void tearDown() {
    }

@Test
    public void testLogOut_normal() throws Exception {
        System.out.println("testLogOut_normal");
        
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
        Header header = new Header(MessageId.LOGOUT, MessageId.LOGOUT.createErrorId(0), 2);
        Message message = new Message(header, "junk");
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
        Accounts accounts = new Accounts(5);
        Account account1 = new Account(user1Nick,user1Pass);
        accounts.addAccount(user1Nick, account1);
        Authentication authentication = new Authentication(accounts.getMap());
        
        //Initialize MessageSender
        OutStreams outStreams = new OutStreams();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outStreams.addStream(userID, new DataOutputStream(baos));
        Emitter emitter = new Emitter();
        
        MessageSender messageSender = new MessageSender(outStreams, emitter);
        
        //Initialize Log_In
        Log_Out messages = new Log_Out();
        
        //Initialize LoggedUtil
                Logged logged = new Logged();
        LoggedUtil loggedUtil = new LoggedUtil(logged);
        loggedUtil.add(userID, account1);
        
        //Initialize RoomManager

        Rooms rooms = new Rooms();
        ChatRoom chatRoom = new ChatRoom(userID,user2ID);
        rooms.addNew(userID, chatRoom);
        RoomManager roomManager = new RoomManager(rooms);
        
        
        //Create LogOut class
        LogOut logOut = new LogOut(stateManager,messageSender, messages,loggedUtil, roomManager);
        logOut.serveEvent(ueMessage);
        Thread.sleep(1000);
        

        assertEquals(UserState.CONNECTED_TO_SERVER, userStates.getUserState(userID));
        assertEquals(false,loggedUtil.isLogged(userID));
        assertEquals(false,roomManager.isFree(userID));

    }
    
}
