package dev.linwood.bucketsystem.app.commands;

import picocli.CommandLine;

@CommandLine.Command (name = "operation", description = "Get operation details")
public class OperationCommand implements Runnable {
    @CommandLine.Parameters (index = "0", description = "Operation ID")
    private String operationId;

    @Override
    public void run() {

    }
}
