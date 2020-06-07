package it.polimi.ingsw.server.controller;

/**
 * Thrown when the selected worker is unable to build in his turn.
 */
public class UnableToMoveException extends Exception {

    public UnableToMoveException() {
    }
/*
    public UnableToMoveException(String message) {
        super(message);
    }

    public UnableToMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToMoveException(Throwable cause) {
        super(cause);
    }

    public UnableToMoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }*/
}
