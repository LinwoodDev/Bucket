package dev.linwood.bucketsystem.app.commands;

import dev.linwood.bucketsystem.api.providers.BucketProvider;
import picocli.CommandLine;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CommandLine.Command(name = "opened", description = "List all opened operations")
public class OpenedOperationsCommand implements Runnable {
    @CommandLine.Option(names = {"--token", "-t"}, description = "Token")
    private String token;
    @CommandLine.Option(names = {"--url", "-u"}, description = "Repository url")
    private URL repositoryUrl;
    @CommandLine.Option(names = {"--provider", "-p"}, description = "Force provider", defaultValue = CommandLine.Parameters.NULL_VALUE)
    private String forceProvider;

    @Override
    public void run() {
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
        System.out.println(String.join(", ", operations.stream().map(String::valueOf).toArray(String[]::new)));
    }
}
