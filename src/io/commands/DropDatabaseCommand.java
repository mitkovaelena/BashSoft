package io.commands;

import   annotations.Alias;
import   annotations.Inject;
import   exceptions.InvalidInputException;
import   io.OutputWriter;
import   repository.StudentsRepository;

@Alias("dropdb")
public class DropDatabaseCommand extends Command {

    @Inject
    private StudentsRepository studentsRepository;

    public DropDatabaseCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 1) {
            throw new InvalidInputException(this.getInput());
        }

        this.studentsRepository.unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");
    }
}
