package dev.linwood.bucketsystem.app;

import dev.linwood.bucketsystem.app.commands.ClearCommand;
import picocli.CommandLine;

@CommandLine.Command (name = "bucketsystem", mixinStandardHelpOptions = true, subcommands = {
        CommandLine.HelpCommand.class,
        ClearCommand.class
})
public class BucketSystemApp {

    public static void main(String[] args) {
        @SuppressWarnings ("InstantiationOfUtilityClass") var exitCode = new CommandLine(new BucketSystemApp()).execute(args);
        System.exit(exitCode);
    }
}
