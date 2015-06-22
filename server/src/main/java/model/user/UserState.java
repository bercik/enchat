package model.user;

/**
 * Defines allowed userStates
 *
 * @author Created by tochur on 16.04.15.
 */
public enum UserState {
    CONNECTED_TO_SERVER,                //Means, that server listen for answer from him.
    LOGGED,                             //Means, that user logged Successfully, have full functionality available and posses unique UserData
    IN_ROOM,                            //Means, that user is assigned to controller.room.
    DISCONNECTED;                       //Means, that sht went wrong. He participate in system.
}
