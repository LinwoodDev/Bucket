package dev.linwood.bucketsystem.app.commands;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "analyze", description = "Analyze the file system")
public class AnalyzeCommand implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The build directory", defaultValue = "build")
    private Path buildDirectory;

    @Override
    public Integer call() {
        System.out.println("Analyzing...");
        return 0;
    }
}
