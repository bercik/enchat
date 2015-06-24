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
public class BGColorCodes
{
    TwoWayHashmap<IFormatter.Color, Integer> map = new TwoWayHashmap<>();

    public BGColorCodes()
    {
        // 3x is normal color
        // 9x is high intensity (brighter) color
        map.add(IFormatter.Color.BLACK, 40);
        map.add(IFormatter.Color.RED, 41);
        map.add(IFormatter.Color.GREEN, 42);
        map.add(IFormatter.Color.YELLOW, 43);
        map.add(IFormatter.Color.BLUE, 44);
        map.add(IFormatter.Color.MAGENTA, 45);
        map.add(IFormatter.Color.CYAN, 46);
        map.add(IFormatter.Color.WHITE, 47);
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
