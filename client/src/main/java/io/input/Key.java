/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.input;

import java.util.Arrays;

/**
 *
 * @author robert
 */
public enum Key
{
    ESC,
    ARROW_UP(91, 65),
    ARROW_DOWN(91, 66),
    DELETE(91, 51, 126),
    UNKNOWN;

    // escape character
    public static final int ESCAPE_CHAR = 27;
    // tablica na sekwencję
    private final int[] escChSeq;

    public static Key getKey(char[] escChSeq)
    {
        // sprawdzamy czy podano nam jakąkolwiek sekwencję
        if (escChSeq.length <= 0)
        {
            throw new RuntimeException("Escape char sequence must be of "
                + "length one or more");
        }
        // sprawdzamy czy zaczyna się od znaku ESCAPE_CHAR
        if (escChSeq[0] != ESCAPE_CHAR)
        {
            throw new RuntimeException("Escape char sequence must start "
                + "from escape char character " + ESCAPE_CHAR + 
                " was " + escChSeq[0]);
        }
        
        // jeżeli w sekwencji znajduje się tylko klawisz escape to zwracamy
        // klawisz escape
        if (escChSeq.length == 1)
        {
            return Key.ESC;
        }
        
        // usuwamy pierwszy znak, który na pewno jest znakiem esc
        escChSeq = Arrays.copyOfRange(escChSeq, 1, escChSeq.length);
        // iterujemy po wszystkich znanych klawiszach specjalnych oprócz
        // klawisza escape, który sprawdziliśmy już wcześniej
        // jeżeli nie znajdziemy zwracamy nieznany
        for (Key k : Key.values())
        {
            if (k != Key.ESC && k.isKey(escChSeq))
            {
                return k;
            }
        }
        
        return Key.UNKNOWN;
    }
    
    private Key(int... eescChSeq)
    {
        escChSeq = eescChSeq;
    }

    private boolean isKey(char[] eescCharSeq)
    {
        // sprawdzamy czy zgadza się z naszym kluczem
        // jeżeli wielkość podanej sekwencji mniejsza od naszej to false
        if (eescCharSeq.length < escChSeq.length)
        {
            return false;
        }
        
        // sprawdzamy po kolei każdy znak
        for (int i = 0; i < escChSeq.length; ++i)
        {
            if (escChSeq[i] != eescCharSeq[i])
                return false;
        }
        
        // inaczej true
        return true;
    }
}
