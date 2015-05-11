package user;

import org.junit.Test;
import room.ChatRoom;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UserTest {
    @Test
    public void doesGetRoomCreatesNewWhenReferenceWasNull() throws Exception {
        User user = mock(User.class);
        doCallRealMethod().when(user).setRoom(any(ChatRoom.class));
        when(user.getRoom()).thenCallRealMethod();

        ChatRoom chatRoom = null;
        user.setRoom(chatRoom);
        assertNotNull("Accessing room didn't create new ChatRoom when it was null", user.getRoom());
    }

    @Test
    public void whenRoomExistsNoModificationAreMade() throws Exception {
        User user = mock(User.class);
        doCallRealMethod().when(user).setRoom(any(ChatRoom.class));
        when(user.getRoom()).thenCallRealMethod();

        ChatRoom chatRoom = mock(ChatRoom.class);
        user.setRoom(chatRoom);
        assertTrue( chatRoom == user.getRoom());
    }
}