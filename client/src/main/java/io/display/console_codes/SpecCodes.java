/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.display.console_codes;

import io.display.IFormatter;
import util.TwoWayHashmap;

/**
 *
 * @author Robert
 */
public class SpecCodes
{
    TwoWayHashmap<IFormatter.SpecialFormat, Integer> map = new TwoWayHashmap<>();
    
    public SpecCodes()
    {
        map.add(IFormatter.SpecialFormat.DIM, 2);
        map.add(IFormatter.SpecialFormat.UNDERSCORE, 4);
    }
    
    public int getCode(IFormatter.SpecialFormat specFormat)
    {
        return map.getForward(specFormat);
    }
    
    public IFormatter.SpecialFormat getSpecialFormat(int code)
    {
        return map.getBackward(code);
    }
    
    public boolean hasCode(int code)
    {
        return map.hasKeyBackward(code);
    }
}
