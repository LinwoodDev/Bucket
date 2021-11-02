package dev.linwood.bucketsystem.app.commands;

import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CommandLine.Command(name = "clear", description = "Clear the build directory")
public class ClearCommand implements Runnable {
    @CommandLine.Parameters(index = "0", description = "The build directory", defaultValue = "build")
    private Path path = Paths.get("build");
    @Override
    public void run() {
        try {
            Files.deleteIfExists(path);
            System.out.println("Build directory cleared");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
