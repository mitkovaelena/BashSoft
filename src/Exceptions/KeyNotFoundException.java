package exceptions;

import staticData.ExceptionMessages;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException() {

        super(ExceptionMessages.NOT_ENROLLED_IN_COURSE);
    }

    public KeyNotFoundException(String message) {
        super(message);
    }
}
