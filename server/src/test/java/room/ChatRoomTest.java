package room;

import message3.MessageSender;
import message3.generators.Conversationalist_Disconnected;
import message3.generators.Messages;
import message3.types.EncryptedMessage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import user.User;

import java.io.DataOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ChatRoomTest {
    private MessageSender messageSender;
    private ChatRoom chatRoom;
    private static Messages messages;
    //private User user; - enough to make room full
    private static User[] uniqueUsers;
    private static User additionalUser;

    @BeforeClass
    public static void classInit(){
        additionalUser = mock(User.class);
        uniqueUsers = new User[]{mock(User.class), mock(User.class), mock(User.class)};
        messages = mock(Messages.class);
    }

    @Before
    public void init(){
        //Util for sending messages
        messageSender = mock(MessageSender.class);
        //Room with max amount of users 3
        chatRoom = new ChatRoom(messageSender, 3, messages);
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

    @Test
    public void removing_user_works() throws Exception {
        Conversationalist_Disconnected conversationalist_disconnectedMock = mock(Conversationalist_Disconnected.class);
        when(messages.conversationalistDisconnected()).thenReturn(conversationalist_disconnectedMock);


        for(User user: uniqueUsers){
            chatRoom.add(user);
        }
        int maxAmount = chatRoom.getParticipantsAmount();

        for(User user: uniqueUsers){
            chatRoom.remove(user);
        }
        int afterRemoving = chatRoom.getParticipantsAmount();

        assertThat(maxAmount, is(3));
        assertThat(afterRemoving, is(0));
    }

    @Test
    public void removing_user_that_not_exist_wont_change_room_state() throws Exception {

        for(User user: uniqueUsers){
            chatRoom.add(user);
        }
        int maxAmount = chatRoom.getParticipantsAmount();


        chatRoom.remove(additionalUser);
        int afterRemoving = chatRoom.getParticipantsAmount();

        assertThat(maxAmount, is(afterRemoving));
        assertThat(maxAmount, is(3));
    }

    @Test
    public void no_message_passed_to_sender() throws Exception {
        chatRoom.add(uniqueUsers[0]);
        EncryptedMessage message = mock(EncryptedMessage.class);

        chatRoom.sendMessageAs(uniqueUsers[0], message);

        verify(messageSender, never()).sendMessage((DataOutputStream)Matchers.any(), (EncryptedMessage)Matchers.any());
    }

    @Test
    public void message_passed_to_other_user() throws Exception {
        for(User user: uniqueUsers){
            chatRoom.add(user);
        }
        EncryptedMessage message = mock(EncryptedMessage.class);

        chatRoom.sendMessageAs(uniqueUsers[0], message);

        verify(messageSender, times(2)).sendMessage((DataOutputStream)Matchers.any(), eq(message));
    }
}