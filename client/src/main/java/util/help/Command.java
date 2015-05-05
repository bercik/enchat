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
public class Command implements Comparable<Command>
{
    private final String name;
    private final String shortDescription;
    private final String description;
    private final Parameter[] parameters;

    @Override
    public int compareTo(Command o)
    {
        return name.compareTo(o.name);
    }
    
    public String getName()
    {
        return name;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public String getDescription()
    {
        return description;
    }

    public Parameter[] getParameters()
    {
        return parameters;
    }

    public String[] getParametersName()
    {
        String[] parametersName = new String[parameters.length];
        int i = 0;
        
        for (Parameter param : parameters)
            parametersName[i++] = param.getName();
        
        return parametersName;
    }
    
    public Command(String name, String description, String shortDescription,
            Parameter[] parameters)
    {
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.parameters = parameters;
    }
}
