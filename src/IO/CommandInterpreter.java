package IO;

import Judge.Tester;
import Network.DownloadManager;
import Repository.StudentsRepository;
import StaticData.ExceptionMessages;
import StaticData.SessionData;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CommandInterpreter {
    private Tester tester;
    private StudentsRepository repository;
    private DownloadManager downloadManager;
    private IOManager inputOutputManager;

    public CommandInterpreter(Tester tester, StudentsRepository repository, DownloadManager downloadManager, IOManager inputOutputManager) {
        this.tester = tester;
        this.repository = repository;
        this.downloadManager = downloadManager;
        this.inputOutputManager = inputOutputManager;
    }

    void interpretCommand(String input) {
        String[] data = input.split("\\s+");
        String command = data[0].toLowerCase();

        try {
            parseCommand(input, data, command);
        }catch (Exception iae){
            OutputWriter.displayException(iae.getMessage());
        }
    }

    private void parseCommand(String input, String[] data, String command) {
        switch (command){
            case "show":
                tryShowWantedCourse(input,data);
                break;
            case "open":
                tryOpenFile(input, data);
                break;
            case "mkdir":
                tryCreateDirectory(input,data);
                break;
            case "ls":
                tryTraverseFolder(input,data);
                break;
            case "cmp":
                tryCompareFiles(input,data);
                break;
            case "changedirrel":
                tryChangeRelativePath(input,data);
                break;
            case "changedirabs":
                tryChangeAbsolutePath(input,data);
                break;
            case "readdb":
                tryReadDatabaseFromFile(input,data);
                break;
            case "filter":
                tryPrintFilteredStudents(input,data);
                break;
            case "order":
                tryPrintOrderedStudents(input,data);
                break;
            case "download":
                tryDownloadFile(command, data);
                break;
            case "downloadasynch":
                tryDownloadFileOnNewThread(command, data);
                break;
            case "help":
                getHelp();
                break;
            default:
                displayInvalidCommand(input);
        }
    }

    private void tryDownloadFile(String command, String[] data) {
        if (data.length != 2) {
            displayInvalidCommand(command);
            return;
        }

        String fileUrl = data[1];
        this.downloadManager.download(fileUrl);
    }

    private void tryDownloadFileOnNewThread(String command, String[] data) {
        if (data.length != 2) {
            displayInvalidCommand(command);
            return;
        }

        String fileUrl = data[1];
        this.downloadManager.downloadOnNewThread(fileUrl);
    }

    private  void tryPrintOrderedStudents(String input, String[] data) {
        if(data.length != 3 && data.length != 4){
            displayInvalidCommand(input);
            return;
        }

        String course = data[1];
        String compareType = data[2];

        if(data.length == 3){
            this.repository.orderAndTake(course, compareType);
            return;
        }

        Integer numberOfStudents = Integer.valueOf(data[3]);
        this.repository.orderAndTake(course, compareType, numberOfStudents);
    }

    private  void tryPrintFilteredStudents(String input, String[] data) {
        if(data.length != 3 && data.length != 4){
            displayInvalidCommand(input);
            return;
        }

        String course = data[1];
        String filter = data[2];

        if(data.length == 3){
            this.repository.filterAndTake(course, filter);
            return;
        }

        Integer numberOfStudents = Integer.valueOf(data[3]);
        this.repository.filterAndTake(course, filter, numberOfStudents);
    }

    private  void tryShowWantedCourse(String input, String[] data) {
        if(data.length != 2 && data.length != 3){
            displayInvalidCommand(input);
            return;
        }

        if(data.length == 2){
            String courseName = data[1];
            this.repository.getStudentsByCourse(courseName);
        }

        if(data.length == 3){
            String courseName = data[1];
            String userName = data[2];
            this.repository.getStudentMarkInCourse(courseName, userName);
        }
    }

    private  void getHelp() {
        OutputWriter.writeMessageOnNewLine("mkdir path - make directory");
        OutputWriter.writeMessageOnNewLine("ls depth - traverse directory");
        OutputWriter.writeMessageOnNewLine("cmp path1 path2 - compare two files");
        OutputWriter.writeMessageOnNewLine("changeDirRel relativePath - change directory");
        OutputWriter.writeMessageOnNewLine("changeDirAbs absolutePath - change directory");
        OutputWriter.writeMessageOnNewLine("readDb path - read students data base");
        OutputWriter.writeMessageOnNewLine("filterExcelent - filter excelent students (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("filterExcelent path - filter excelent students (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("filterAverage - filter average students (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("filterAverage path - filter average students (the output is written in a file)");
        OutputWriter.writeMessageOnNewLine("filterPoor - filter low grade students (the output is on the console)");
        OutputWriter.writeMessageOnNewLine("filterPoor path - filter low grade students (the output is written in a file)");
        OutputWriter.writeMessageOnNewLine("order - sort students in increasing order (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("order path - sort students in increasing order (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("decOrder - sort students in decreasing order (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("decOrder path - sort students in decreasing order (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("download pathOfFile - download file (saved in current directory)");
        OutputWriter.writeMessageOnNewLine("downloadAsync path - download file asynchronously (save in the current directory)");
        OutputWriter.writeMessageOnNewLine("help - get help");
        OutputWriter.writeEmptyLine();
    }

    private  void tryReadDatabaseFromFile(String input, String[] data) {
        if(data.length != 2){
            displayInvalidCommand(input);
            return;
        }

        String fileName = data[1];
        this.repository.loadData(fileName);
    }

    private  void tryChangeAbsolutePath(String input, String[] data) {
        if(data.length != 2){
            displayInvalidCommand(input);
            return;
        }

        String relPath = data[1];
        this.inputOutputManager.changeCrntDirAbsolutePath(relPath);
    }

    private  void tryChangeRelativePath(String input, String[] data) {
        if(data.length != 2){
            displayInvalidCommand(input);
            return;
        }

        String relPath = data[1];
        this.inputOutputManager.changeCrntDirRelativePath(relPath);
    }

    private  void tryCompareFiles(String input, String[] data) {
        if(data.length != 3){
            displayInvalidCommand(input);
            return;
        }

        String firstPath = data[1];
        String secondPath = data[2];
        this.tester.compareContent(firstPath,secondPath);

    }

    private  void tryTraverseFolder(String input, String[] data) {
        if(data.length != 1 && data.length != 2){
            displayInvalidCommand(input);
            return;
        }

        if(data.length == 1){
            this.inputOutputManager.traverseFolder(0);
        }

        if(data.length == 2){
            int depth = Integer.parseInt(data[1]);
            this.inputOutputManager.traverseFolder(depth);
        }
    }

    private  void tryCreateDirectory(String input, String[] data) {
        if(data.length != 2){
            displayInvalidCommand(input);
            return;
        }

        String folderName = data[1];
        this.inputOutputManager.createDirectoryInCurrentFolder(folderName);
    }

    private  void displayInvalidCommand(String input) {
        OutputWriter.displayException(String.format("The command '%s' is invalid", input));
    }

    private  void tryOpenFile(String input, String[] data) {
        if(data.length != 2){
            displayInvalidCommand(input);
            return;
        }

        try {
        String fileName = data[1];
        String filePath = SessionData.currentPath + "\\" + fileName;
        File file = new File(filePath);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            OutputWriter.displayException(ExceptionMessages.ERROR_OPENING_FILE);
        }
    }
}
