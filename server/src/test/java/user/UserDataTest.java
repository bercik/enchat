package user;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDataTest {
    static UserData data;
    static UserData theSameReference;
    static UserData theSameNickAndPassword;
    static UserData differentPassword;
    static UserData differentLogin;
    static UserData bothDifferent;

    @BeforeClass
    public static void before(){
        data = new UserData("login", "password");
        theSameReference = data;
        theSameNickAndPassword = new UserData("login","password");
        bothDifferent = new UserData("otherLogin","otherPassword");
        differentLogin = new UserData("otherLogin","password");
        differentPassword = new UserData("login","otherPassword");
    }

    @Test
    public void whenReferenceTheSame() throws Exception {
        assertThat(data.equals(theSameReference), is(true));
    }

    @Test
    public void whenTheSameNickAndPassword() throws Exception {
        assertThat(data.equals(theSameNickAndPassword), is(true));
    }

    @Test
    public void whenPasswordAndLoginDifferent() throws Exception {
        assertThat(data.equals(differentLogin), is(false));
    }

    @Test
    public void whenLoginDifferent() throws Exception {
        assertThat(data.equals(differentLogin), is(false));
    }

    @Test
    public void whenPasswordDifferent() throws Exception {
        assertThat(data.equals(bothDifferent), is(false));
    }
}