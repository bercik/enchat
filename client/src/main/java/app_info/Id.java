/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info;

import app_info.exceptions.IncorrectIdException;

/**
 * Typ wyliczeniowy zawierający numery id (typu int) odpowiadające poszczególnym
 * pluginom i kontrolerom, nie związanym z wiadomościami wymenianymi między
 * klientem, a serwerem. Id musi być mniejsze od zera (pozostałe są
 * zarezerwowane dla protokołu komunikacyjnego klient-serwer).
 *
 * @author robert
 */
public enum Id
{
    // id musi być mniejsze od zera!
    MAIN_CONTROLLER(-1),
    HELP_PLUGIN(-2),
    CALC_PLUGIN(-3);

    private int id;

    private Id(int iid)
    {
        if (iid >= 0)
            throw new IncorrectIdException("Id powinno być mniejsze od zera, "
                    + "a ma wartość " + Integer.toString(id));
        
        id = iid;
    }

    public static Id createId(int iid)
    {
        for (Id id : Id.values())
        {
            if (id.getIntRepresentation() == iid)
            {
                return id;
            }
        }
        
        throw new IncorrectIdException("Plugin ani kontroler o podanym ID"
                + " nie istnieją");
    }
    
    public int getIntRepresentation()
    {
        return id;
    }
}
