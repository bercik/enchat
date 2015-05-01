package user;

/**
 * Created by tochur on 16.04.15.
 *
 * Defines allowed userStates
 */
public enum UserState {
    CONNECTED_TO_SERVER,                //Means, that server listen for answer from him.
    AFTER_KEY_EXCHANGE,                 //Means, that communication server - client is enabled, but user is not logged after public key exchange
    LOGGED,                             //Means, that user logged Successfully, and have full functionality available.
    IN_ROOM,                            //Means, that user is assigned to room.
    DISCONNECTED                        //Means, that user should be removed from ActiveUser list
}
