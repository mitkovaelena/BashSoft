import io.CommandInterpreter;
import io.IOManager;
import io.InputReader;
import io.OutputWriter;
import judge.Tester;
import network.DownloadManager;
import repository.RepositoryFilter;
import repository.RepositorySorter;
import repository.StudentsRepository;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {

        Tester tester = new Tester();
        DownloadManager downloadManager = new DownloadManager();
        IOManager ioManager = new IOManager();
        RepositorySorter sorter = new RepositorySorter();
        RepositoryFilter filter = new RepositoryFilter();
        StudentsRepository repository = new StudentsRepository(filter,sorter);
        CommandInterpreter currentInterpreter = new CommandInterpreter(tester,repository,downloadManager,ioManager);
        InputReader reader = new InputReader(currentInterpreter);

        try {
            reader.readCommands();
        } catch (Exception e){
            OutputWriter.displayException(e.getMessage());
        }
    }
}
