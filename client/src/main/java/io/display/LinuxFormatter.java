package io.display;

/**
 * This class has static functions which format Strings (targets).
 * Tested in Linux Terminal.
 * Usage: Formatter.bg(Formatter.MAGENTA, "text to colorize");
 * @author kamil
 */
public class LinuxFormatter implements IFormatter
{
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
        switch (color)
        {
            case BLACK:
                return addSpecialAttribute(target, 30);
            case RED:
                return addSpecialAttribute(target, 31);
            case GREEN:
                return addSpecialAttribute(target, 32);
            case YELLOW:
                return addSpecialAttribute(target, 33);
            case BLUE:
                return addSpecialAttribute(target, 34);
            case MAGENTA:
                return addSpecialAttribute(target, 35);
            case CYAN:
                return addSpecialAttribute(target, 36);
            case WHITE:
                return addSpecialAttribute(target, 37);
        }
        return target;
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
        switch (color)
        {
            case BLACK:
                return addSpecialAttribute(target, 40);
            case RED:
                return addSpecialAttribute(target, 41);
            case GREEN:
                return addSpecialAttribute(target, 42);
            case YELLOW:
                return addSpecialAttribute(target, 43);
            case BLUE:
                return addSpecialAttribute(target, 44);
            case MAGENTA:
                return addSpecialAttribute(target, 45);
            case CYAN:
                return addSpecialAttribute(target, 46);
            case WHITE:
                return addSpecialAttribute(target, 47);
        }
        return target;
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
        switch (sf)
        {
            case DIM:
                return addSpecialAttribute(target, 2);
            case UNDERSCORE:
                return addSpecialAttribute(target, 4);
        }
        return target;
    }

    private String addSpecialAttribute(String target, int code)
    {
        return "\033[" + code + "m" + target + "\033[0m";
    }
}