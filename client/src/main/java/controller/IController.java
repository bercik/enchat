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
    
    public void update(String[] parameters);
    
    public void updateError(int error);
}
