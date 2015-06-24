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
public class FGColorCodes
{
    TwoWayHashmap<IFormatter.Color, Integer> map = new TwoWayHashmap<>();
    
    public FGColorCodes()
    {
        // 3x is normal color
        // 9x is high intensity (brighter) color
        map.add(IFormatter.Color.BLACK, 90);
        map.add(IFormatter.Color.RED, 91);
        map.add(IFormatter.Color.GREEN, 32);
        map.add(IFormatter.Color.YELLOW, 33);
        map.add(IFormatter.Color.BLUE, 94);
        map.add(IFormatter.Color.MAGENTA, 95);
        map.add(IFormatter.Color.CYAN, 96);
        map.add(IFormatter.Color.WHITE, 97);
    }
    
    public int getCode(IFormatter.Color color)
    {
        return map.getForward(color);
    }
    
    public IFormatter.Color getColor(int code)
    {
        return map.getBackward(code);
    }
    
    public boolean hasCode(int code)
    {
        return map.hasKeyBackward(code);
    }
}
