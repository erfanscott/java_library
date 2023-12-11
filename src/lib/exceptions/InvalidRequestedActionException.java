package lib.exceptions;

public class InvalidRequestedActionException extends Exception {
    public InvalidRequestedActionException() {
        super();
    }

    public InvalidRequestedActionException(String message) {
        super(message);
    }
}
