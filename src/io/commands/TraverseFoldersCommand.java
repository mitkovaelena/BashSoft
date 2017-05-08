package io.commands;

import   annotations.Alias;
import   annotations.Inject;
import   exceptions.InvalidInputException;
import   io.IOManager;


@Alias("ls")
public class TraverseFoldersCommand extends Command {

    @Inject
    private IOManager ioManager;

    public TraverseFoldersCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 1 && data.length != 2) {
            throw new InvalidInputException(this.getInput());
        }

        if (data.length == 1) {
            this.ioManager.traverseFolder(0);
            return;
        }

        this.ioManager.traverseFolder(Integer.valueOf(data[1]));
    }
}
