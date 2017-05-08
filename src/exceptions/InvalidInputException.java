package exceptions;

import staticData.ExceptionMessages;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super(ExceptionMessages.INVALID_COMMAND_MESSAGE);
}

    public InvalidInputException(String message) {
        super(message);
    }
}
