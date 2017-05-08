package io.commands;

import   annotations.Alias;
import   annotations.Inject;
import   exceptions.InvalidInputException;
import   network.DownloadManager;

@Alias("downloadasynch")
public class DownloadAsynchCommand extends Command {

    @Inject
    private DownloadManager downloadManager;

    public DownloadAsynchCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 2) {
            throw new InvalidInputException(this.getInput());
        }

        String fileUrl = data[1];
        this.downloadManager.downloadOnNewThread(fileUrl);
    }
}
