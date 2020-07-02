package it.polimi.ingsw.server.controller;

/**
 * Thrown when the selected worker is unable to build in his turn.
 */
public class UnableToMoveException extends Exception {

    private String errorCode;

    public UnableToMoveException() {
    }

    public UnableToMoveException(String error) {
        this.errorCode = error;
    }

    public String getErrorCode() {
        return errorCode;
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
