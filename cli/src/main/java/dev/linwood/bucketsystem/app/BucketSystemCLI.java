package dev.linwood.bucketsystem.app;

import dev.linwood.bucketsystem.app.commands.*;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Paths;

@CommandLine.Command (name = "bucketsystem", mixinStandardHelpOptions = true, subcommands = {
        CommandLine.HelpCommand.class,
        AnalyzeCommand.class,
        BuildCommand.class,
        OpenedOperationsCommand.class,
        CreateCommand.class,
        OperationCommand.class
})
public class BucketSystemCLI {

    public static void main(String[] args) {
        @SuppressWarnings ("InstantiationOfUtilityClass") var exitCode = new CommandLine(new BucketSystemCLI()).execute(args);
        System.exit(exitCode);
    }

    public static BucketConfig getBucketConfig() throws IOException {
        return BucketConfig.load(Paths.get("bucket.json"));
    }
}
