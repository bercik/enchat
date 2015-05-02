package plugin;

import app_info.State;
import controller.ControllerManager;
import package_forwarder.MessageIncomeBuffer;

/**
 * Created by mateusz on 02.05.15.
 */
public class PluginManager {

    //kostruktor który zapisuje referencje do obiektu tyou MessageIncomeBuffer
    public PluginManager(MessageIncomeBuffer mmessageIncomeBuffer) {
        messageIncomeBuffer = mmessageIncomeBuffer;
    }

    public void setAppState(State newAppState) {
        controllerManager.setAppState(newAppState);
    }

    private MessageIncomeBuffer messageIncomeBuffer;
    private ControllerManager controllerManager;
}
