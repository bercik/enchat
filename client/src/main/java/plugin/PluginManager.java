package plugin;

import app_info.IPluginCommandContainer;
import app_info.State;
import controller.ControllerManager;
import io.display.IDisplay;
import messages.MessageId;
import package_forwarder.MessageIncomeBuffer;
import package_forwarder.PackageForwarder;

/**
 * @author robert
 */
public class PluginManager
{
    // TODO
    
    private final PackageForwarder packageForwarder;
    private final MessageIncomeBuffer messageIncomeBuffer;
    private final IPluginCommandContainer pluginCommandContainer;
    private ControllerManager controllerManager;
    
    public PluginManager(MessageIncomeBuffer mmessageIncomeBuffer, 
            IPluginCommandContainer ppluginCommandContainer,
            PackageForwarder ppackageForwarder)
    {
        // unchanging reference to some core objects
        messageIncomeBuffer = mmessageIncomeBuffer;
        pluginCommandContainer = ppluginCommandContainer;
        packageForwarder = ppackageForwarder;
        
        // iterate over all plugins and set plugin manager in them
        for (IPlugin plugin : pluginCommandContainer.getAllPlugins())
        {
            plugin.setPluginManager(this);
        }
    }

    public void updatePlugin(int id, String[] parameters)
    {
        IPlugin plugin = pluginCommandContainer.getPluginById(id);
        plugin.update(MessageId.ErrorId.OK.getIntRepresentation(),
                parameters);
    }
    
    public void send(int id, String[] parameters)
    {
        try
        {
            packageForwarder.send(id, parameters);
        }
        catch (Exception ex)
        {
            exceptionOccured();
        }
    }
    
    public void connect()
    {
        try
        {
            packageForwarder.connect();
        }
        catch (Exception ex)
        {
            exceptionOccured();
        }
    }
    
    public void setMsg(String msg, boolean error)
    {
        controllerManager.setMsg(msg, error);
    }
    
    public void setDisplay(int callerId, IDisplay newDisplay)
    {
        controllerManager.setDisplay(callerId, newDisplay);
    }
    
    public void setAppState(State newAppState)
    {
        controllerManager.setAppState(newAppState);
    }

    public void updateControllerError(int error)
    {
        controllerManager.updateControllerError(error);
    }
    
    public void setControllerManager(ControllerManager ccontrollerManager)
    {
        controllerManager = ccontrollerManager;
    }
    
    /**
     * Metoda wywoływana za każdym obiegiem pętli głównej programu.
     * Sprawdza czy są jakieś nowe paczki od serwera w MessageIncomeBuffer 
     * i jeżeli tak to przekazuje je do odpowiedniego pluginu.
     */
    public void update()
    {
        // TODO
    }
    
    private void exceptionOccured()
    {
        // zmieniamy stan aplikacji na not_connected
        // (package_forwarder gwarantuje, że w przypadku wyjątku połączenie
        // z serwerem zostaje przerwane i całość wraca do stanu sprzed
        // połączenia)
        controllerManager.setAppState(State.NOT_CONNECTED);
        // resetujemy wszystkie pluginy
        for (IPlugin plugin : pluginCommandContainer.getAllPlugins())
            plugin.reset();
    }
}
