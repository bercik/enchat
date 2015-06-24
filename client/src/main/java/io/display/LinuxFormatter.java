package io.display;

import io.display.console_codes.BGColorCodes;
import io.display.console_codes.FGColorCodes;
import io.display.console_codes.SpecCodes;

/**
 * This class has static functions which format Strings (targets).
 * Tested in Linux Terminal.
 * Usage: Formatter.bg(Formatter.MAGENTA, "text to colorize");
 * @author kamil
 */
public class LinuxFormatter implements IFormatter
{
    // kody
    private final FGColorCodes fgColorCodes = new FGColorCodes();
    private final BGColorCodes bgColorCodes = new BGColorCodes();
    private final SpecCodes specCodes = new SpecCodes();
    
    /**
        * Use this function to change foreground (font) of String.
        * Fg abbreviation comes from foreground.
        * @param color
        *   enum defined in Color
        * @param target
        *   String which we want to colorize
        * @return 
        *   colorized String
        */
    @Override
    public String fg(Color color, String target)
    {
        return addSpecialAttribute(target, fgColorCodes.getCode(color));
    }

    /**
        * Use this function to change background of String.
        * Bg abbreviation comes from background
        * @param color
        *   enum defined in Color
        * @param target
        *   String which we want to colorize (its background)
        * @return 
        *   colorized background of String
        */
    @Override
    public String bg(Color color, String target)
    {
        return addSpecialAttribute(target, bgColorCodes.getCode(color));
    }

    /**
        * Use this function to change background of String.
        * Spec abbreviation comes from special formating
        * @param sf
        *   enum defined in SpecialFormat
        * @param target
        *   String which we want to format
        * @return 
        *   formatted String
        */
    @Override
    public String spec(SpecialFormat sf, String target)
    {
        return addSpecialAttribute(target, specCodes.getCode(sf));
    }

    private String addSpecialAttribute(String target, int code)
    {
        return "\033[" + code + "m" + target + "\033[0m";
    }
}
