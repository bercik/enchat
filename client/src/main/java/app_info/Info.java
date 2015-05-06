/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info;

/**
 *
 * @author robert
 */
public class Info
{
    // singleton class
    private static Info instance = null;
    
    public static Info getInstance()
    {
        if (instance == null)
        {
            instance = new Info();
        }
        
        return instance;
    }
    
    private Info()
    {

    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getInterlocutorName()
    {
        return interlocutorName;
    }

    public void setInterlocutorName(String interlocutorName)
    {
        this.interlocutorName = interlocutorName;
    }
    
    private String userName = null;
    private String interlocutorName = null;
}
