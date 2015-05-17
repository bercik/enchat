/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author robert
 */
public interface IController
{
    public void putChar(char ch);
    
    public void putEscapeCharSequence(char[] escChSeq);
    
    /**
     * Metoda wywoływana za każdym razem gdy IController jest ustawiany
     * jako currentController w ControllerManager.
     * 
     * @param previousCommand komenda w poprzednim kontrolerze
     * @param parameters  parametry
     */
    public void start(String previousCommand, String[] parameters);
    
    public int getId();
    
    public void setId(int iid);
    
    /**
     * Metoda umożliwiająca przekazanie informacji o błędach do kontrolera
     * przez np. plugin.
     * 
     * @param error
     */
    public void updateError(int error);
    
    public String getCommand();
    
    public void setControllerManager(ControllerManager ccontrollerManager);
    
    /**
     * Metoda wywoływana za każdym razem kiedy IController jest zmieniany
     * na inny przez ControllerManagera.
     */
    public void reset();
}
