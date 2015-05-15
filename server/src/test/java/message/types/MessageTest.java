package message.types;

import message3.types.Message;
import messages.MessageId;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void allElementsWereAddedToMessage() throws Exception {
        List<String> nick = Arrays.asList(new String[]{"One", "Two", "Three" });
        Message message = new Message(MessageId.JUNK, MessageId.JUNK.createErrorId(0), nick.size(), nick);

        for(String s: nick){
            assertTrue(message.getPackages().contains(s));
        }
        assertTrue(message.getPackages().size() == nick.size());
    }

    @Test(expected = java.lang.AssertionError.class)
    public void modificationOfInfoPassedToMessageNotPullTheMessage() throws Exception {
        List<String> nick = Arrays.asList(new String[]{"One", "Two", "Three" });
        Message message = new Message(MessageId.JUNK, MessageId.JUNK.createErrorId(0), nick.size(), nick);

        nick.set(2, "BUG");

        for(String s: nick){
            assertTrue(message.getPackages().contains(s));
        }
    }
}