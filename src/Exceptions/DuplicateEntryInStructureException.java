package Exceptions;

import StaticData.ExceptionMessages;

public class DuplicateEntryInStructureException extends RuntimeException {
    public DuplicateEntryInStructureException(String entryNAme, String structureName) {
        super(String.format(ExceptionMessages.STUDENT_ALREADY_ENROLLED_IN_GIVEN_COURSE, entryNAme, structureName));
    }

    public DuplicateEntryInStructureException(String message) {
        super(message);
    }
}
