package dev.linwood.bucketsystem.app.commands;

import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command (name = "build", description = "Builds the bucket")
public class BuildCommand implements Runnable {
    @CommandLine.Parameters (index = "0", description = "The build directory", defaultValue = "build")
    private Path buildDirectory;

    @Override
    public void run() {
        System.out.println("Building the bucket...");
    }
}
