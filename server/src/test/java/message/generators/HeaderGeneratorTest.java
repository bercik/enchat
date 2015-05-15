package message.generators;

import message3.generators.HeaderGenerator;
import message3.types.Header;
import messages.IncorrectErrorId;
import messages.IncorrectMessageId;
import messages.MessageId;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HeaderGeneratorTest {
    @Test
    public void isHeaderConstructedCorrectlyWithValidErrorIdArguments() throws IncorrectMessageId {
        //SIGN_UP = 2 errors (0-4)
        //when
        MessageId id = MessageId.SIGN_UP;
        int errorId = 0;
        Header header = HeaderGenerator.createHeader(id, errorId, 1);

        //then
        assertThat(header.getId(), is(MessageId.SIGN_UP));
        assertThat(header.getErrorId(), is(id.createErrorId(0)));
        assertThat(header.getPackageAmount(), is(1));
    }

    @Test(expected = IncorrectErrorId.class)
    public void headerWontBeCreatedWhenHeaderErrorIdIsIncorrect() throws IncorrectMessageId {
        //when
        MessageId id = MessageId.SIGN_UP;
        MessageId.ErrorId errorId = id.createErrorId(5);
    }
}