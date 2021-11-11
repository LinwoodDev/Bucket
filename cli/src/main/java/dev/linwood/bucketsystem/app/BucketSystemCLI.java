package dev.linwood.bucketsystem.app;

import dev.linwood.bucketsystem.app.commands.ClearCommand;
import dev.linwood.bucketsystem.app.commands.OpenedOperationsCommand;
import picocli.CommandLine;

@CommandLine.Command (name = "bucketsystem", mixinStandardHelpOptions = true, subcommands = {
        CommandLine.HelpCommand.class,
        OpenedOperationsCommand.class,
        ClearCommand.class
})
public class BucketSystemCLI {

    public static void main(String[] args) {
        @SuppressWarnings ("InstantiationOfUtilityClass") var exitCode = new CommandLine(new BucketSystemCLI()).execute(args);
        System.exit(exitCode);
    }
}
