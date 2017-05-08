package io.commands;

import annotations.Alias;
import annotations.Inject;
import exceptions.InvalidInputException;
import io.IOManager;

@Alias("cdabs")
public class ChangeAbsolutePathCommand extends Command {

    @Inject
    private IOManager ioManager;

    public ChangeAbsolutePathCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 2) {
            throw new InvalidInputException(this.getInput());
        }

        String absolutePath = data[1];
        this.ioManager.changeCrntDirAbsolutePath(absolutePath);
    }
}
