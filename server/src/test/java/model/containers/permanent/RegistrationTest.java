package model.containers.permanent;

import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;


/*
 * Integration test (test both class Registration and Accounts).
 * Now Accounts class is trivial, so it's not necessary to write unit test for this class.
 * But in the future it may be replaced by class that reads from file or connects with DB.
 * When it increase it's complexity there should appear additional unit test for
 * Registration & Accounts.
 */
public class RegistrationTest {
    private Registration registration;
    private static Accounts accounts;
    private static List<String> uniqueNicks;
    private static List<String> passwords;

    @BeforeClass
    public static void initClass(){
        //Initialization of nicks
        String[] nicks = new String[] {"First", "Second", "Third", "Forth"};
        uniqueNicks = new ArrayList<String>(Arrays.asList(nicks));

        //Passwords initialization
        String[] pass = new String[] {"Password1", "Password2", "Password3", "Password4"};
        passwords = new ArrayList<String>(Arrays.asList(pass));
    }

    @Before
    public void init(){
        accounts = new Accounts(3);
        registration = new Registration(accounts);
    }

    @Test
    public void register_adds_elements_to_Accounts() throws Exception {
        for(int i = 0; i<3; i++){
            registration.register(uniqueNicks.get(i), passwords.get(i));
        }

        assertEquals(accounts.getAmount(), 3);
    }

    @Test
    public void register_adds_right_elements_to_Accounts() throws Exception {
        for(int i = 0; i<3; i++){
            registration.register(uniqueNicks.get(i), passwords.get(i));
        }

        Set<String> nicks = accounts.getNicks();
        for(int i = 0; i<3; i++){
            assertTrue(nicks.contains(uniqueNicks.get(i)));
        }
    }

    @Test
    public void can_add_max_amount_of_accounts() throws Exception {
        for(int i = 0; i<3; i++){
            registration.register(uniqueNicks.get(i), passwords.get(i));
        }
    }

    @Test(expected = OverloadedCannotAddNew.class)
    public void cannot_add_more_then_max_amount_accounts() throws Exception {
        for(int i = 0; i<4; i++){
            registration.register(uniqueNicks.get(i), passwords.get(i));
        }
    }

    @Test(expected = AlreadyInCollection.class)
    public void can_add_only_account_with_unique_nick() throws Exception {
        registration.register(uniqueNicks.get(1), passwords.get(1));
        registration.register(uniqueNicks.get(1), passwords.get(1));
    }
}