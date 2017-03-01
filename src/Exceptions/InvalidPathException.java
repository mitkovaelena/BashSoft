package Exceptions;

import StaticData.ExceptionMessages;

public class InvalidPathException extends RuntimeException {
    public InvalidPathException() {

        super(ExceptionMessages.INVALID_PATH);
    }

    public InvalidPathException(String message) {
        super(message);
    }
}
