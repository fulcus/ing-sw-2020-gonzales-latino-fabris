package it.polimi.ingsw.server.controller;

/**
 * Thrown when the selected worker is unable to build in his turn.
 */
public class UnableToMoveException extends Exception {

    //errorCode "lose" makes player lose at first exception instead of switching worker
    private String errorCode;

    public UnableToMoveException() {
    }

    public UnableToMoveException(String error) {
        this.errorCode = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
