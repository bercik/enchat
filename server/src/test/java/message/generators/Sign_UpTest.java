package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class Sign_UpTest {

    @Test
    public void created_busy_login_message_is_Created_Correctly() throws Exception {
        EncryptedMessage answer = Sign_Up.busyLogin();

        assertThat(answer.getId(), is(MessageId.SIGN_UP));
        assertThat(answer.getErrorId(), is(MessageId.SIGN_UP.createErrorId(1)));
        assertThat(answer.getPackageAmount(), is(0));
    }
}