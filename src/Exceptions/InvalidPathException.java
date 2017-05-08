package exceptions;

import staticData.ExceptionMessages;

public class InvalidPathException extends RuntimeException {
    public InvalidPathException() {

        super(ExceptionMessages.INVALID_PATH);
    }

    public InvalidPathException(String message) {
        super(message);
    }
}
