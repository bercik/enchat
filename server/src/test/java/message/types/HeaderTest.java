package message.types;

import messages.IncorrectMessageId;
import messages.MessageId;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeaderTest {
    @Test
    public void isHeaderConstructedCorrectlyWithValidArguments() throws IncorrectMessageId {
        //SIGN_UP = 2 errors (0-3)
        //when
        MessageId id = MessageId.createMessageId(2);
        MessageId.ErrorId errorId = id.createErrorId(0);
        Header header = new Header(id, errorId, 1);

        //then
        assertThat(header.getId(), is(MessageId.SIGN_UP));
        assertThat(header.getErrorId(), is(errorId));
        assertThat(header.getPackageAmount(), is(1));
    }

    @Test(expected = IncorrectMessageId.class)
    public void headerWontBeCreatedWhenHeaderIdIsIncorrect() throws IncorrectMessageId {
        //when
        MessageId id = MessageId.createMessageId(15);
        MessageId.ErrorId errorId = id.createErrorId(0);
    }

   @Test(expected = IncorrectMessageId.class)
    public void headerWontBeCreatedWhenHeaderErrorIdIsIncorrect() throws IncorrectMessageId {
        //when
        MessageId id = MessageId.createMessageId(2);
        MessageId.ErrorId errorId = id.createErrorId(4);
    }
}