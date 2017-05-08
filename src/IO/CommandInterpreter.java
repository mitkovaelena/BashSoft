package   io;

import   annotations.Alias;
import   annotations.Inject;
import   io.commands.Executable;
import   judge.Tester;
import   network.DownloadManager;
import   repository.StudentsRepository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandInterpreter {

    private static final String COMMAND_LOCATION = "src/io/commands";
    private static final String COMMAND_PACKAGE = "io.commands.";
    private Tester tester;
    private StudentsRepository repository;
    private DownloadManager downloadManager;
    private IOManager ioManager;

    public CommandInterpreter(Tester tester,
                              StudentsRepository repository,
                              DownloadManager downloadManager,
                              IOManager ioManager) {
        this.tester = tester;
        this.repository = repository;
        this.downloadManager = downloadManager;
        this.ioManager = ioManager;
    }

    public void interpretCommand(String input) throws IOException {
        String[] data = input.split("\\s+");
        String commandName = data[0].toLowerCase();
        try {
            Executable command = parseCommand(input, data, commandName);
            command.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            OutputWriter.displayException(ex.getMessage());
        }
    }

    private Executable parseCommand(String input, String[] data, String command) {
        File commandFolder = new File(COMMAND_LOCATION);
        Executable executable = null;

        for (File file : commandFolder.listFiles()) {
            if (!file.isFile() || !file.getName().endsWith(".java")) {
                continue;
            }
            try {
                String className = file.getName().substring(0, file.getName().lastIndexOf("."));

                Class<Executable> exeClass = (Class<Executable>) Class.forName(COMMAND_PACKAGE + className);

                if(!exeClass.isAnnotationPresent(Alias.class)) {
                    continue;
                }

                Alias alias = exeClass.getAnnotation(Alias.class);
                String value = alias.value();

                if(!value.equalsIgnoreCase(command)) {
                    continue;
                }

                Constructor exeCtor = exeClass.getConstructor(String.class, String[].class);
                executable = (Executable) exeCtor.newInstance(input, data);
                this.injectDependencies(executable, exeClass);
            } catch (ReflectiveOperationException rfoe) {
                rfoe.printStackTrace();
            }
        }
        return executable;

    }

    private void injectDependencies(Executable executable, Class<Executable> exeClass) throws ReflectiveOperationException {
        Field[] exeFields = exeClass.getDeclaredFields();
        for (Field exeField : exeFields) {
            if(!exeField.isAnnotationPresent(Inject.class)) {
                continue;
            }
            exeField.setAccessible(true);
            Field[] theseFields = CommandInterpreter.class.getDeclaredFields();
            for (Field thisField : theseFields) {
                if(!thisField.getType().equals(exeField.getType())) {
                    continue;
                }
                thisField.setAccessible(true);
                exeField.set(executable, thisField.get(this));
            }
        }
    }
}