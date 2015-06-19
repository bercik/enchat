package model.containers.temporary;

import controller.responders.exceptions.ToMuchUsersInThisRoom;
import model.ChatRoom;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class RoomManagerTest {
    private RoomManager roomManager;
    private Rooms rooms;

   /* @Before
    public void init(){
        rooms = mock(Rooms.class);
        roomManager = new RoomManager(rooms);
    }

    @Test
    public void testIsFree() throws Exception {
        //when
        roomManager.isFree(5);

        //then
        verify(rooms, times(1)).isUserInRoom(5);
    }

    @Test
    public void testStartConversation() throws Exception {
        //when
        roomManager.startConversation(10, 123510);

        //then
        verify(rooms, times(2)).addNew(anyInt(), any(ChatRoom.class));
    }

    @Test(expected = ToMuchUsersInThisRoom.class)
    public void cannot_create_conversation_when_user_is_talking_with_another_user() throws Exception {
        //given
        when(rooms.isUserInRoom(anyInt())).thenReturn(true);

        //when
        roomManager.startConversation(13, 123510);
    }

    @Test
    public void testLeaveRoom() throws Exception {
        //given
        ChatRoom chatRoom = mock(ChatRoom.class);
        Set<Integer> set = new HashSet<>();
        set.add(1234);
        when(chatRoom.getParticipantsIDs()).thenReturn(set);
        when(rooms.getUserRoom(anyInt())).thenReturn(chatRoom);
        when(rooms.isUserInRoom(anyInt())).thenReturn(true);

        //when
        Collection<Integer> ids = roomManager.leaveRoom(10);

        //then
        assertTrue(ids.contains(1234));
        assertTrue(ids.size() == 1);
        verify(rooms, times(1)).remove(10);
    }

    @Test
    public void testGetConversationalists() throws Exception {
        //given
       /* Integer[] idsInRoom = new Integer[] {12, 34, 67, 12, 1, 3};
        ChatRoom chatRoom = mock(ChatRoom.class);
        when(chatRoom.getParticipantsIDs()).thenReturn(Arrays.asList(idsInRoom));
        when(rooms.isUserInRoom(anyInt())).thenReturn(true);

        //when
        Collection<Integer> others = roomManager.leaveRoom(67);

        //then
        assertThat(others.size(), is(idsInRoom.length - 1));
        assertFalse(others.contains(67));
    }

    @Test
    public void testLeaveRoomAndTryToInform() throws Exception {

    }*/
}