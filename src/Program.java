import IO.CommandInterpreter;
import IO.IOManager;
import IO.InputReader;
import IO.OutputWriter;
import Judge.Tester;
import Network.DownloadManager;
import Repository.RepositoryFilter;
import Repository.RepositorySorter;
import Repository.StudentsRepository;

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
