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
public class Command
{
    private final String name;
    private final String description;
    private final Parameter[] parameters;

    public String getName()
    {
        return name;
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
    
    public Command(String name, String description, Parameter[] parameters)
    {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }
}
