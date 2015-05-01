package containers;

import containers.exceptions.OverloadedCannotAddNew;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 01.05.15.
 */
public class BlackList {
    private List<String> nicks;
    private final int MAX_SIZE;

    public BlackList(){
        this(100);
    }

    public BlackList(int maxSize){
        MAX_SIZE = maxSize;
        nicks = new LinkedList<String>();
    }

    /**
     * Add new nick to black list.
     * @param nick no message from user with this nick, wont be delivered
     */
    public void add(String nick) throws OverloadedCannotAddNew {
        if(MAX_SIZE > nicks.size()){
            nicks.add(nick);
        }else {
            throw new OverloadedCannotAddNew();
        }

    }

    public void remove(String nick){
        for(int i = 0; i < nicks.size(); i++){
            if( nick.equals(nicks.get(i))){
                nicks.remove(i);
            }
        }
    }

    /**
     * Cheek weather specified nick is on black List
     * @param nick - cheeked nick
     * @return - answer
     */
    public boolean hasNick(String nick){
        for(String s: nicks){
            if( s.equals(nick)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return Independent Collection Of Nicks from blackList
     */
    public String[] getNicks(){ return nicks.toArray(new String[0]); }
}
