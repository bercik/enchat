/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author robert
 */
public interface IState
{

    /**
     * Powinna wyrzucać wyjątek RunMethodNotImplementedException jeżeli
     * nie jest zaimplementowana.
     * 
     * @param param
     * @return
     */
    public IState run(String param);
    
    /**
     * Powinna wyrzucać wyjątek RunMethodNotImplementedException jeżeli
     * nie jest zaimplementowana.
     * 
     * @param parameters
     * @return
     */
    public IState run(String[] parameters);
    
    /**
     * Powinna wyrzucać wyjątek RunMethodNotImplementedException jeżeli
     * nie jest zaimplementowana.
     * 
     * @param param1
     * @param parameters
     * @return
     */
    public IState run(String param1, String[] parameters);
}
