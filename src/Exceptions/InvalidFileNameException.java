package Exceptions;

import StaticData.ExceptionMessages;

public class InvalidFileNameException extends RuntimeException {
    public InvalidFileNameException() {
        super(ExceptionMessages.FORBIDDEN_SYMBOLS_CONTAINED_IN_NAME);
    }

    public InvalidFileNameException(String message) {
        super(message);
    }
}
