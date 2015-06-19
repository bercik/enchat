package model.containers.temporary;

import model.ChatRoom;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RoomsTest {
   /* private Rooms room;
    private static ChatRoom chatRoom;
    private static ChatRoom chatRoom2;

    @BeforeClass
    public static void initClass(){
        chatRoom = mock(ChatRoom.class);
        chatRoom2 = mock(ChatRoom.class);
    }

    @Before
    public void init(){
        room = new Rooms();
    }

    @Test
    public void each_user_id_may_be_in_collection_only_once() throws Exception {
        room.addNew(5, chatRoom);
        room.addNew(6, chatRoom2);
        room.addNew(6, chatRoom);

        assertEquals(room.getMap().size(), 2);
        assertTrue("Adding element with the same userID (key) didn't modify ",
                room.getMap().get(6) == chatRoom);
    }

    @Test
    public void able_to_remove_record_with_user() throws Exception {
        room.addNew(5, chatRoom);
        room.addNew(6, chatRoom2);
        room.remove(5);

        assertEquals(room.getMap().size(), 1);
        assertTrue("Adding element with the same userID (key) didn't modify ",
                room.getMap().get(6) == chatRoom2);
    }

    @Test
    public void testIsUserInRoom() throws Exception {
        room.addNew(5, chatRoom);

        assertTrue(room.isUserInRoom(5));
    }

    @Test
    public void testGetUserRoom() throws Exception {
        room.addNew(6, chatRoom);

        assertTrue("Accessing userRoom with function failed",
                room.getUserRoom(6) == chatRoom);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cannot_modify_collection_returned() throws Exception {
        room.addNew(6, chatRoom);

        room.getMap().put(7, chatRoom2);
    }

    @Test
    public void can_read_correct_values_from_returned_collection() throws Exception {
        room.addNew(6, chatRoom);

        assertTrue(room.getMap().get(6) == chatRoom);
        room.addNew(7, chatRoom2);

        assertTrue("Returned map not changed it's state, when there are some changes on model.",
                room.getMap().size() == 2);
    }*/
}