package io;

import exceptions.InvalidFileNameException;
import exceptions.InvalidPathException;
import staticData.SessionData;

import java.io.File;
import java.util.ArrayDeque;

public class IOManager {

    public void traverseFolder(int depth){
        ArrayDeque<File> fileQueue = new ArrayDeque<>();

        String path = SessionData.currentPath;
        int initialIndentation = path.split("\\\\").length;

        File root = new File(path);

        fileQueue.offer(root);

        while (!fileQueue.isEmpty()){
            File crntDir = fileQueue.poll();
            int crntIndentation = crntDir.toString().split("\\\\").length - initialIndentation;

            if(depth - crntIndentation < 0){
                break;
            }

            OutputWriter.writeMessageOnNewLine(crntDir.toString());

            try {
                for (File f : crntDir.listFiles()) {
                    if (f.isDirectory()) {
                        fileQueue.offer(f);
                    } else{
                        int indOfLastSlash = f.toString().lastIndexOf("\\");
                        for (int i = 0; i < indOfLastSlash; i++) {
                            OutputWriter.writeMessage("-");
                        }
                        OutputWriter.writeMessageOnNewLine(f.getName());
                    }
                }
            } catch (Exception e){
                OutputWriter.writeMessageOnNewLine("Access denied!");
            }
        }
    }

    public void createDirectoryInCurrentFolder(String name) {
        String path = getCrntDirPath() + "\\" + name;
        File file = new File(path);
        boolean wasDirMade = file.mkdir();
        if (!wasDirMade) {
            throw new InvalidFileNameException();
        }
    }

    public String getCrntDirPath() {
        return SessionData.currentPath;
    }

    public void changeCrntDirRelativePath(String relativePath){
        String crntDir = SessionData.currentPath;
        if(relativePath.equals("..")){
            try {
                int indOfLastSlash = crntDir.toString().lastIndexOf("\\");
            String newPath = crntDir.substring(0,indOfLastSlash);
            SessionData.currentPath = newPath;
            } catch (StringIndexOutOfBoundsException sioobe) {
                throw new InvalidPathException();
            }
        } else {
            crntDir += "\\" + relativePath;
            changeCrntDirAbsolutePath(crntDir);
        }
    }

    public void changeCrntDirAbsolutePath(String absolutePath) {
        File file = new File(absolutePath);
        if(!file.exists()){
            throw new InvalidPathException();
        }
        SessionData.currentPath = absolutePath;
    }
}
