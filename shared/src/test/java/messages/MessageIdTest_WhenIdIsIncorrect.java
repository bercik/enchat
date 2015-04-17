package messages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MessageIdTest_WhenIdIsIncorrect {
    private int id;

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Object[][] data = new Object[][] {{-20}, {-1}, {13}, {1111100}};
        return Arrays.asList(data);
    }

    public MessageIdTest_WhenIdIsIncorrect(int id){
        this.id = id;
    }


    @Test(expected = IncorrectMessageId.class)
    public void testCreateMessageId() throws Exception {
        MessageId messageId = MessageId.createMessageId(id);
    }
}