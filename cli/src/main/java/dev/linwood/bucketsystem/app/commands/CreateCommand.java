package dev.linwood.bucketsystem.app.commands;

import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@CommandLine.Command(name = "clear", description = "Clear the build directory")
public class CreateCommand implements Runnable {
    @Override
    public void run() {
        try {
            Files.deleteIfExists(Paths.get("build"));
            System.out.println("Build directory cleared");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
