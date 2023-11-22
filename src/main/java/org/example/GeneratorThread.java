package org.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GeneratorThread extends Thread{
    private static final String LIST_OF_NUMS = "0123456789";
    private static final String FILENAME = "savedIds.txt";
    private static final String INIT_ID = "00000000%n";
    private static final int LENGTH = 8;
    private final int counter;

    public GeneratorThread(int i) {
        this.counter = i;
    }

    @Override
    public void run() {
        try (FileWriter fs = new FileWriter(FILENAME, true); BufferedWriter bw = new BufferedWriter(fs)) {
            List<String> lines = Files.readAllLines(Paths.get(FILENAME));
            checkIfEmpty(lines, bw);
            boolean check = false;
            for (int i = 1; i <= counter; i++) {
                String generatedId = RandomStringUtils.random(LENGTH, LIST_OF_NUMS);
                for(String line : lines) {
                    if (!line.matches(generatedId)) {
                        check = true;
                    } else {
                        check = false;
                        --i;
                        break;
                    }
                }
                if (check) {
                    System.out.println(i + ". " + generatedId);
                    bw.append(String.format(generatedId + "%n"));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkIfEmpty(List<String> lines, BufferedWriter bw) throws IOException {
        if (lines.isEmpty()) {
            bw.append(String.format(INIT_ID));
            lines.add(String.format(INIT_ID));
        }
    }
}
