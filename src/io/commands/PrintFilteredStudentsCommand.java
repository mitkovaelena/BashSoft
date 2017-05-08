package io.commands;

import   annotations.Alias;
import   annotations.Inject;
import   exceptions.InvalidInputException;
import   io.OutputWriter;
import   repository.StudentsRepository;
import   staticData.ExceptionMessages;

;

@Alias("filter")
public class PrintFilteredStudentsCommand extends Command {

    @Inject
    private StudentsRepository repository;

    public PrintFilteredStudentsCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 5) {
            throw new InvalidInputException(this.getInput());
        }

        String course = data[1];
        String filter = data[2].toLowerCase();
        String takeCommand = data[3].toLowerCase();
        String takeQuantity = data[4].toLowerCase();

        this.tryParseParametersForFilter(takeCommand, takeQuantity, course, filter);
    }

    private void tryParseParametersForFilter(
            String takeCommand, String takeQuantity,
            String courseName, String filter) {
        if (!takeCommand.equals("take")) {
            OutputWriter.displayException(ExceptionMessages.INVALID_COMMAND_MESSAGE);
            return;
        }

        if (takeQuantity.equals("all")) {
            this.repository.filterAndTake(courseName, filter);
            return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.repository.filterAndTake(courseName, filter, studentsToTake);
        } catch (NumberFormatException nfe) {
            OutputWriter.displayException(ExceptionMessages.INVALID_COMMAND_MESSAGE);
        }
    }
}
