/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.help;

/**
 *
 * @author robert
 */
public class Information implements Comparable<Information>
{
    private final String name;
    private final String description;
    private final String shortDescription;

    @Override
    public int compareTo(Information o)
    {
        return name.compareTo(o.name);
    }
    
    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }
    
    public Information(String name, String description, String shortDescription)
    {
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
    }
}
