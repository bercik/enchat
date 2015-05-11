package message.generators;

import message.types.EncryptedMessage;
import message.types.Message;
import message.utils.Encryption;
import messages.MessageId;
import org.junit.Test;
import room.ChatRoom;
import user.User;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Black_ListTest {

    @Test
    public void Correct_Header_And_Encrypting_method_Invoked() throws Exception {
      /*  User user = mock(User.class);
        String[] nicks = new String[]{"One", "Two", "Three"};
        Encryption encryption  = mock(Encryption.class);

        EncryptedMessage answer = Black_List.blackList(user, nicks);

        assertThat(answer.getId(), is(MessageId.BLACK_LIST));
       // assertThat(answer.getErrorId(), is(MessageId.SIGN_UP.createErrorId(1)));
       // assertThat(answer.getPackageAmount(), is(0));*/
    }
}