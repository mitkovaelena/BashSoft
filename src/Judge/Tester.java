package judge;

import io.OutputWriter;
import staticData.ExceptionMessages;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tester {

    public void compareContent(String actualOutpput, String expectedOutput) {
        try {
            OutputWriter.writeMessageOnNewLine("Reading files...");
            String mismatchPath = getMismatchPath(expectedOutput);

            List<String> actualOutputString = readTextFile(actualOutpput);
            List<String> expectedOutputString = readTextFile(expectedOutput);

            boolean mismatch = compareStrings(actualOutputString, expectedOutputString, mismatchPath);
            if (mismatch) {
                List<String> mismatchString = readTextFile(mismatchPath);
                mismatchString.forEach(OutputWriter::writeMessageOnNewLine);
            } else {
                OutputWriter.writeMessageOnNewLine("Both files are identical. There are no mismatches");
            }
        } catch (Exception e) {
        throw new exceptions.InvalidPathException();
      }
    }

    private boolean compareStrings(List<String> actualOutpputString, List<String> expectedOutputString, String mismatchPath) {
        OutputWriter.writeMessageOnNewLine("Comparing files...");
        String output = "";
        boolean isMismatch = false;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(mismatchPath))){

            for (int i = 0; i < expectedOutputString.size(); i++) {
                String actualLine = actualOutpputString.get(i);
                String expectedLine = expectedOutputString.get(i);

                if (actualLine.equals(expectedLine)){
                    output += String.format("line match -> %s%n", actualLine);
                } else{
                    output += String.format("mismatch -> expected{%s}, actual{%s}%n",expectedLine,  actualLine);
                    isMismatch = true;
                }
            }

            if (isMismatch) {
                writer.write(output);
            }


        } catch (Exception e) {
            throw new IllegalArgumentException(ExceptionMessages.ERROR_COMPARING_FILES);
        }
        return isMismatch;
    }

    private List<String> readTextFile(String filePath) {
        List<String> text =  new ArrayList<>();
        File file = new File(filePath);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                text.add(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionMessages.FILE_NOT_FOUND);
        }

        return text;
    }

    private String getMismatchPath(String expectedOutput){
        int index = expectedOutput.lastIndexOf('\\');
        String dirPath = expectedOutput.substring(0,index);
        return dirPath + "\\mismatch.txt";
    }
}
