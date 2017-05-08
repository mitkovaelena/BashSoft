package io;

import staticData.SessionData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {
    private static final String END_COMMAND = "quit";
    CommandInterpreter interpreter;

    public InputReader(CommandInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void readCommands() throws IOException {
        OutputWriter.writeMessage(String.format("%s >" , SessionData.currentPath));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine().trim();

        while (!input.equals(END_COMMAND)){
            this.interpreter.interpretCommand(input);
            OutputWriter.writeMessage(String.format("%s >" , SessionData.currentPath));
            input = reader.readLine().trim();
        }
    }
}
