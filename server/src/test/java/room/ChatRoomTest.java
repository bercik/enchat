package room;

import message.MessageSender;
import message.types.EncryptedMessage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import user.User;

import javax.jws.soap.SOAPBinding;
import java.io.DataOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ChatRoomTest {
    private static MessageSender messageSender;
    private ChatRoom chatRoom;
    //private User user; - enough to make room full
    private static User[] uniqueUsers;
    private static User additionalUser;

    @BeforeClass
    public static void classInit(){
        additionalUser = mock(User.class);
        uniqueUsers = new User[]{mock(User.class), mock(User.class), mock(User.class)};
    }

    @Before
    public void init(){
        //Util for sending messages
        messageSender = mock(MessageSender.class);
        //Room with max amount of users 3
        chatRoom = new ChatRoom(messageSender, 3);
    }

    @Test
    public void adding_not_unique_user_wont_increase_users_in_room_number() throws Exception {
        for(int i = 0; i<3; i++){
            chatRoom.add(additionalUser);
        }
        assertThat(chatRoom.getParticipantsAmount(), is(1));
    }

    @Test
    public void ability_To_Add_Max_Amount_Of_User() throws Exception {
        for(User user: uniqueUsers){
            chatRoom.add(user);
        }
    }

    @Test(expected = ToMuchUsersInThisRoom.class)
    public void inability_To_Add_More_Than_Max_Amount_Of_User() throws Exception {
        for(User user: uniqueUsers){
            chatRoom.add(user);
        }
        chatRoom.add(additionalUser);
    }

    /*@Test
    public void removing_user_works() throws Exception {
        ChatRoom spy = spy(chatRoom);
        int i = 0;
        for(User user: uniqueUsers){
            i++;
            when(user.getNick()).thenReturn(Integer.toString(i));
        }
        //doNothing().when(spy).remove(any(User.class));
        doNothing().when(spy).sendMessageAs(any(User.class), any(EncryptedMessage.class));

        for(User user: uniqueUsers){
            spy.add(user);
        }
        int maxAmount = spy.getParticipantsAmount();

        for(User user: uniqueUsers){
            spy.remove(user);
        }
        int afterRemoving = spy.getParticipantsAmount();

        assertThat(maxAmount, is(3));
        assertThat(afterRemoving, is(0));
    }*/

    @Test
    public void no_message_passed_to_sender() throws Exception {
        chatRoom.add(uniqueUsers[0]);
        EncryptedMessage message = mock(EncryptedMessage.class);

        chatRoom.sendMessageAs(uniqueUsers[0], message);

        verify(messageSender, never()).sendMessage(any(DataOutputStream.class),any(EncryptedMessage.class));
    }

    @Test
    public void message_passed_to_other_user() throws Exception {
        for(User user: uniqueUsers){
            chatRoom.add(user);
        }
        EncryptedMessage message = mock(EncryptedMessage.class);

        chatRoom.sendMessageAs(uniqueUsers[0], message);

        verify(messageSender, times(2)).sendMessage(any(DataOutputStream.class),eq(message));
    }
}