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
    MAIN_CONTROLLER(),
    HELP_PLUGIN(),
    CALC_PLUGIN(),
    CONNECT_PLUGIN(),
    DISCONNECT_PLUGIN(),
    STATE_PLUGIN();

    // bez znaczenia co tu damy i tak będzie 0
    private static int idCounter = 0;

    // ugly workaround

    private static int getIdCounter()
    {
        return --idCounter;
    }

    private int id;

    private Id()
    {
        // ugly workaround
        id = getIdCounter();
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
