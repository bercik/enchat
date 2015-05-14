package user;

/**
 * Created by tochur on 16.04.15.
 *
 * Defines allowed userStates
 */
public enum UserState {
    CONNECTED_TO_SERVER,                //Means, that newServer listen for answer from him.
    AFTER_KEY_EXCHANGE,                 //Means, that communication newServer - client is enabled, but user is not logged after public key exchange
    LOGGED,                             //Means, that user logged Successfully, have full functionality available and posses unique UserData
    IN_ROOM,                            //Means, that user is assigned to room.
    TO_DISCONNECT,                      //Means, that user should be removed from ActiveUser list, and all necessary works should by done
    DISCONNECTED;                       //Means, that sth is wrong, active user should already not exist.
}
