package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GeneratorThread extends Thread implements Constans{

    private final int counter;
    public GeneratorThread(int i) {
        this.counter = i;
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
            List<String> lines = Files.readAllLines(Paths.get(FILENAME));
            checkIfEmpty(lines, bw);
            for (int i = 1; i <= counter; i++) {
                String generatedId = RandomStringUtils.random(LENGTH, LIST_OF_NUMS);
                if (!lines.contains(generatedId)) {
                    System.out.println(i + ". " + generatedId);
                    bw.append(String.format(generatedId + "%n"));
                    lines.add(String.format(generatedId + "%n"));
                } else {
                    --i;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkIfEmpty(@NotNull List<String> lines, BufferedWriter bw) throws IOException {
        if (lines.isEmpty()) {
            bw.append(String.format(INIT_ID));
            lines.add(String.format(INIT_ID));
        }
    }
}
