/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_info;

import app_info.exceptions.CommandAlreadyExistsException;
import app_info.exceptions.IdAlreadyExistsException;
import app_info.exceptions.NullCommandException;
import controller.ControllerManager;
import controller.IController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import plugin.IPlugin;
import plugin.PluginManager;

/**
 *
 * @author robert
 */
public class CommandContainerTest
{
    // classes used to test
    private static class TestPlugin implements IPlugin
    {
        @Override
        public int getId()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        @Override
        public void reset()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void update(int error, String[] parameters)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setPluginManager(PluginManager pluginManager)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setId(int id)
        {
        }
    }
    private static class TestController implements IController
    {

        @Override
        public void setId(int iid)
        {
        }
        
        
        @Override
        public void putChar(char ch)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void start(String previousCommand, String[] parameters)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getCommand()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void updateError(int error)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setControllerManager(ControllerManager ccontrollerManager)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getId()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        @Override
        public void reset()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    // end of classes used to tests

    public CommandContainerTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of registerCommand method, of class CommandContainer.
     */
    @org.junit.Test
    public void testRegisterCommand() throws Exception
    {
        System.out.println("registerCommand");
        int id = 0;
        String command = "";
        IPlugin plugin = null;
        IController controller = null;
        State[] possibleStates = null;
        CommandContainer instance = new CommandContainer();
        try
        {
            instance.registerCommand(id, command, plugin, controller,
                    possibleStates);
            fail("registerCommand didn't throw NullCommandException");
        }
        catch (NullCommandException ex)
        {
        }

        plugin = new TestPlugin();
        controller = new TestController();
        command = "test";
        possibleStates = new State[]
        {
            State.NOT_CONNECTED, State.CONNECTED
        };
        instance.registerCommand(id, command, plugin, controller,
                possibleStates);

        command = "test2";
        try
        {
            instance.registerCommand(id, command, plugin, controller,
                    possibleStates);
            fail("registerCommand didn't throw IdAlreadyExistsException");
        }
        catch (IdAlreadyExistsException ex)
        {
        }

        id = 1;
        command = "test";
        try
        {
            instance.registerCommand(id, command, plugin, controller,
                    possibleStates);
            fail("registerCommand didn't throw CommandAlreadyExistsException");
        }
        catch (CommandAlreadyExistsException ex)
        {
        }
    }

    /**
     * Test of registerPlugin method, of class CommandContainer.
     */
    @org.junit.Test
    public void testRegisterPlugin() throws Exception
    {
        System.out.println("registerPlugin");
        int id = 0;
        IPlugin plugin = null;
        State[] possibleStates = null;
        CommandContainer instance = new CommandContainer();
        try
        {
            instance.registerPlugin(id, plugin, possibleStates);
            fail("registerPlugin didn't throw NullCommandException");
        }
        catch (NullCommandException ex)
        {
        }

        plugin = new TestPlugin();
        possibleStates = new State[]
        {
            State.CONNECTED, State.CONVERSATION
        };
        instance.registerPlugin(id, plugin, possibleStates);

        try
        {
            instance.registerPlugin(id, plugin, possibleStates);
            fail("registerPlugin didn't throw IdAlreadyExistsException");
        }
        catch (IdAlreadyExistsException ex)
        {
        }
    }

    /**
     * Test of getAllPlugins method, of class CommandContainer.
     */
    @org.junit.Test
    public void testGetAllPlugins() throws Exception
    {
        System.out.println("getAllPlugins");
        CommandContainer instance = new CommandContainer();
        IPlugin[] expResult = new IPlugin[]
        {
            new TestPlugin(), new TestPlugin()
        };
        State[] possibleStates = new State[]
        {
            State.CONNECTED
        };

        for (int i = 0; i < expResult.length; ++i)
        {
            instance.registerPlugin(i, expResult[i], possibleStates);
        }

        IPlugin[] result = instance.getAllPlugins();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getPluginById method, of class CommandContainer.
     */
    @org.junit.Test
    public void testGetPluginById() throws Exception
    {
        System.out.println("getPluginById");
        int id = 10;
        CommandContainer instance = new CommandContainer();
        IPlugin expResult = new TestPlugin();
        instance.registerPlugin(id, expResult, null);
        IPlugin result = instance.getPluginById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIdByString method, of class CommandContainer.
     */
    @org.junit.Test
    public void testGetIdByString() throws Exception
    {
        System.out.println("getIdByString");
        String command = "test";
        int expResult = -40;
        CommandContainer instance = new CommandContainer();
        IPlugin plugin = new TestPlugin();
        instance.registerCommand(expResult, command, plugin, null, null);
        Configuration conf = Configuration.getInstance();
        int result = instance.getIdByString(conf.getCommandPrefix() +
                command);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasPlugin method, of class CommandContainer.
     */
    @org.junit.Test
    public void testHasPlugin() throws Exception
    {
        System.out.println("hasPlugin");
        int id = 0;
        IPlugin plugin = new TestPlugin();
        IController controller = new TestController();
        CommandContainer instance = new CommandContainer();

        instance.registerPlugin(id, plugin, null);
        boolean expResult = true;
        boolean result = instance.hasPlugin(id);
        assertEquals(expResult, result);

        id = 1;
        String command = "test1";
        instance.registerCommand(id, command, plugin, null, null);
        expResult = true;
        result = instance.hasPlugin(id);
        assertEquals(expResult, result);

        id = 2;
        command = "test2";
        instance.registerCommand(id, command, null, controller, null);
        expResult = false;
        result = instance.hasPlugin(id);
        assertEquals(expResult, result);

        id = 3;
        command = "test3";
        instance.registerCommand(id, command, plugin, controller, null);
        expResult = true;
        result = instance.hasPlugin(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasController method, of class CommandContainer.
     */
    @org.junit.Test
    public void testHasController() throws Exception
    {
        System.out.println("hasController");
        IPlugin plugin = new TestPlugin();
        IController controller = new TestController();
        CommandContainer instance = new CommandContainer();

        int id = 1;
        String command = "test1";
        instance.registerCommand(id, command, plugin, null, null);
        boolean expResult = false;
        boolean result = instance.hasController(id);
        assertEquals(expResult, result);

        id = 2;
        command = "test2";
        instance.registerCommand(id, command, null, controller, null);
        expResult = true;
        result = instance.hasController(id);
        assertEquals(expResult, result);

        id = 3;
        command = "test3";
        instance.registerCommand(id, command, plugin, controller, null);
        expResult = true;
        result = instance.hasController(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of commandExists method, of class CommandContainer.
     */
    @org.junit.Test
    public void testCommandExists() throws Exception
    {
        System.out.println("commandExists");
        String command = "test";
        IPlugin plugin = new TestPlugin();
        int id = 0;
        CommandContainer instance = new CommandContainer();
        instance.registerCommand(id, command, plugin, null, null);
        boolean expResult = true;
        boolean result = instance.commandExists("/" + command);
        assertEquals(expResult, result);

        expResult = false;
        result = instance.commandExists("/test2");
        assertEquals(expResult, result);
    }

    /**
     * Test of isCommand method, of class CommandContainer.
     */
    @org.junit.Test
    public void testIsCommand()
    {
        System.out.println("isCommand");
        String command = "/test";
        CommandContainer instance = new CommandContainer();
        boolean expResult = true;
        boolean result = instance.isCommand(command);
        assertEquals(expResult, result);

        command = "bla bla";
        expResult = false;
        result = instance.isCommand(command);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkCommandAvailability method, of class CommandContainer.
     */
    @org.junit.Test
    public void testCheckCommandAvailability() throws Exception
    {
        System.out.println("checkCommandAvailability");
        int id = 0;
        State state = State.CONNECTED;
        State[] possibleStates = new State[]
        {
            State.CONNECTED, State.LOGGED
        };
        IPlugin plugin = new TestPlugin();
        CommandContainer instance = new CommandContainer();
        instance.registerPlugin(id, plugin, possibleStates);
        boolean expResult = true;
        boolean result = instance.checkCommandAvailability(id, state);
        assertEquals(expResult, result);
        
        state = State.CONVERSATION;
        expResult = false;
        result = instance.checkCommandAvailability(id, state);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllControllers method, of class CommandContainer.
     */
    @org.junit.Test
    public void testGetAllControllers() throws Exception
    {
        System.out.println("getAllControllers");
        CommandContainer instance = new CommandContainer();
        IController[] expResult = new IController[]
        {
            new TestController(), new TestController()
        };
        State[] possibleStates = new State[]
        {
            State.CONNECTED
        };

        for (int i = 0; i < expResult.length; ++i)
        {
            instance.registerCommand(i, Integer.toString(i),null, expResult[i], 
                    possibleStates);
        }

        IController[] result = instance.getAllControllers();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getControllerById method, of class CommandContainer.
     */
    @org.junit.Test
    public void testGetControllerById() throws Exception
    {
        System.out.println("getControllerById");
        int id = 0;
        CommandContainer instance = new CommandContainer();
        IController expResult = new TestController();
        instance.registerCommand(id, null, null, expResult, null);
        IController result = instance.getControllerById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of registerController method, of class CommandContainer.
     */
    @Test
    public void testRegisterController() throws Exception
    {
        System.out.println("registerController");
        int id = 0;
        IController controller = null;
        State[] possibleStates = null;
        CommandContainer instance = new CommandContainer();
        try
        {
            instance.registerController(id, controller, possibleStates);
            fail("registerController didn't throw NullCommandException");
        }
        catch (NullCommandException ex)
        {
        }

        controller = new TestController();
        possibleStates = new State[]
        {
            State.CONNECTED, State.CONVERSATION
        };
        instance.registerController(id, controller, possibleStates);

        try
        {
            instance.registerController(id, controller, possibleStates);
            fail("registerController didn't throw IdAlreadyExistsException");
        }
        catch (IdAlreadyExistsException ex)
        {
        }
    }
}