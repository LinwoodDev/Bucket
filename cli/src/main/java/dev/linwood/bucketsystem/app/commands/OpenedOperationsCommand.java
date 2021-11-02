package dev.linwood.bucketsystem.app.commands;

import picocli.CommandLine;

import java.net.URL;

@CommandLine.Command(name = "opened", description = "List all opened operations")
public class OpenedOperationsCommand implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Token")
    private String token;
    @CommandLine.Parameters(index = "1", description = "Repository url")
    private URL repositoryUrl;
    @Override
    public void run() {
        System.out.print("Loading...");

    }
}
