package Exceptions;

import StaticData.ExceptionMessages;

public class InvalidStringException extends RuntimeException {
    public InvalidStringException() {

        super(ExceptionMessages.NULL_OR_EMPTY_VALUE);
    }

    public InvalidStringException(String message) {
        super(message);
    }
}
