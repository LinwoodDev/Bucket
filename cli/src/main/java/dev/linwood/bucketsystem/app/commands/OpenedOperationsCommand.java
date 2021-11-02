package dev.linwood.bucketsystem.app.commands;

import dev.linwood.bucketsystem.providers.BucketProvider;
import picocli.CommandLine;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CommandLine.Command(name = "opened", description = "List all opened operations")
public class OpenedOperationsCommand implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Token")
    private String token;
    @CommandLine.Parameters(index = "1", description = "Repository url")
    private URL repositoryUrl;
    @CommandLine.Parameters(index = "2", description = "Force provider", defaultValue = CommandLine.Parameters.NULL_VALUE)
    private String forceProvider;
    @Override
    public void run() {
        System.out.print("Loading...");
        var provider = BucketProvider.getProviderByURL(repositoryUrl, token, forceProvider);
        assert provider != null;
        List<Integer> operations = null;
        try {
            operations = provider.getOpenedOperationsId().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert operations != null;
        System.out.println("Opened operations:");
        operations.forEach(System.out::println);
    }
}
