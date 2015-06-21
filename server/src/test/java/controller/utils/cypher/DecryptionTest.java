package controller.utils.cypher;

import message.types.Header;
import message.types.Message;
import message.types.UEMessage;
import message.types.UMessage;
import messages.MessageId;
import model.containers.temporary.PublicKeys;
import model.containers.temporary.PublicKeysManager;
import org.junit.Test;
import rsa.KeyContainer;
import rsa.PrivateKeyInfo;
import rsa.PublicKeyInfo;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DecryptionTest {
    private static Integer userID = 5;
    private static Integer userID2 = 10;

   // @Test
    public void testDecryptMessage() throws Exception {
        //given
        KeyContainer serverKeyContainer = new KeyContainer();
        PublicKeyInfo serverPublicKeyInfo = serverKeyContainer.getPublicKeyInfo();
        PrivateKeyInfo serverPrivateKeyInfo = serverKeyContainer.getPrivateKeyInfo();

        KeyContainer clientKeyContainer = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo = clientKeyContainer.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo = clientKeyContainer.getPublicKeyInfo();

        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {"NICK", "PASSWORD"};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(userID, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());

        //when
        //******Decrypting
        DecryptionUtil decryptionUtil = new DecryptionUtil(serverPrivateKeyInfo.getPrivateKey());
        PublicKeysManager publicKeysManager = new PublicKeysManager(new PublicKeys());
        //Adding public key to collection.
        publicKeysManager.addKey(userID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());

        Decryption decryption = new Decryption(decryptionUtil, publicKeysManager);
        UMessage uMessage1 = decryption.decryptMessage(ueMessage);


        //then
        assertEquals(uMessage1.getPackages().get(0), "NICK");
        assertEquals(uMessage1.getPackages().get(1), "PASSWORD");
    }

  //  @Test
    public void testDecryptMessageFromTwoUsers() throws Exception {
        //given
        KeyContainer serverKeyContainer = new KeyContainer();
        PublicKeyInfo serverPublicKeyInfo = serverKeyContainer.getPublicKeyInfo();
        PrivateKeyInfo serverPrivateKeyInfo = serverKeyContainer.getPrivateKeyInfo();

        KeyContainer clientKeyContainer = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo = clientKeyContainer.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo = clientKeyContainer.getPublicKeyInfo();

        KeyContainer clientKeyContainer2 = new KeyContainer();
        PrivateKeyInfo clientPrivateKeyInfo2 = clientKeyContainer.getPrivateKeyInfo();
        PublicKeyInfo clientPublicKeyInfo2 = clientKeyContainer.getPublicKeyInfo();

        //******Encrypting
        //Creating message
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {"NICK", "PASSWORD"};
        Message message = new Message(header, Arrays.asList(info));
        UMessage uMessage = new UMessage(userID, message);
        UMessage uMessage2 = new UMessage(userID2, message);

        //Encrypting message
        EncryptionUtil encryptionUtil = new EncryptionUtil(clientPrivateKeyInfo.getPrivateKey());
        UEMessage ueMessage = encryptionUtil.encryptMessage(uMessage, serverPublicKeyInfo.getPublicKey());

        //Encrypting message 2
        EncryptionUtil encryptionUtil2 = new EncryptionUtil(clientPrivateKeyInfo2.getPrivateKey());
        UEMessage ueMessage2 = encryptionUtil2.encryptMessage(uMessage2, serverPublicKeyInfo.getPublicKey());

        //when
        //******Decrypting
        DecryptionUtil decryptionUtil = new DecryptionUtil(serverPrivateKeyInfo.getPrivateKey());
        PublicKeysManager publicKeysManager = new PublicKeysManager(new PublicKeys());
        //Adding public key to collection.
        publicKeysManager.addKey(userID, clientPublicKeyInfo.getPublicKey(), clientPrivateKeyInfo.getModulus(), clientPrivateKeyInfo.getExponent());
        publicKeysManager.addKey(userID2, clientPublicKeyInfo2.getPublicKey(), clientPrivateKeyInfo2.getModulus(), clientPrivateKeyInfo2.getExponent());

        Decryption decryption = new Decryption(decryptionUtil, publicKeysManager);
        UMessage uMessage1 = decryption.decryptMessage(ueMessage);
        UMessage answerMessage2 = decryption.decryptMessage(ueMessage);


        //then
        assertEquals(uMessage1.getPackages().get(0), "NICK");
        assertEquals(uMessage1.getPackages().get(1), "PASSWORD");

        assertEquals(answerMessage2.getPackages().get(0), "NICK");
        assertEquals(answerMessage2.getPackages().get(1), "PASSWORD");
    }
}