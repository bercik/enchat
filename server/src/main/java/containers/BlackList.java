package containers;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 01.05.15.
 */
public class BlackList {
    private List<String> nicks;

    public BlackList(){
        nicks = new LinkedList<String>();
    }

    /**
     * Add new nick to black list.
     * @param nick no message from user with this nick, wont be delivered
     */
    public void add(String nick){
        nicks.add(nick);
    }

    public void remove(String nick){

    }

    public List<String> getNicks(){
        return nicks;
    }
}
