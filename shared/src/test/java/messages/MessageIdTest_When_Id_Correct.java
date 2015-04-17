package messages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class MessageIdTest_When_Id_Correct {
    private int id;

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Object[][] data = new Object[][] {{0}, {5}, {12}};
        return Arrays.asList(data);
    }

    public MessageIdTest_When_Id_Correct(int id){
        this.id = id;
    }

    @Test
    public void creatingEnumFromCorrectIntValue() throws Exception {
        //when
        MessageId messageId = MessageId.createMessageId(id);

        //then
        if (id == 0)
            assertThat(messageId, is(MessageId.JUNK));
        if (id == 5)
            assertThat(messageId, is(MessageId.CLIENT_MESSAGE));
        if (id == 12)
            assertThat(messageId, is(MessageId.DISCONNECT));
    }
}