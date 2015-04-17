package user;

/**
 * Created by tochur on 16.04.15.
 */
public enum UserState {
    CONNECTED_TO_SERVER,        //Means, that communication server - client is enabled, but user is not logged
    LOGGED,                     //Means, that user is logged, and have full functionality available.
    CONNECTED_WITH_OTHER,       //Means, that user hold talk with sbd
    DISCONNECTED
}
