package user;

/**
 * Created by tochur on 16.04.15.
 *
 * Defines allowed userStates
 */
public enum UserState {
    UNINITIALIZED,                      //Means, that user is not yet initialized correctly and waits for is NOT on ActiveUser list.
    CONNECTED_TO_SERVER,                //Means, that server listen for answer from him.
    AFTER_KEY_EXCHANGE,                 //Means, that communication server - client is enabled, but user is not logged after public key exchange
    LOGGED,                             //Means, that user logged Successfully, and have full functionality available.
    IN_ROOM,                            //Means, that user is assigned to room.
    TO_DISCONNECT,                      //Means, that user should be removed from ActiveUser list, and all necessary works should by done
    DISCONNECTED;                       //Means, that sth is wrong, active user should already not exist.
}
