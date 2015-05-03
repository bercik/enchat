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
public class Parameter
{
    private final String name;
    private final String description;

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Parameter(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
}
