package dev.linwood.bucketsystem.api;

import dev.linwood.bucketsystem.api.operations.BucketOperation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BucketBindings {
    CompletableFuture<@Nullable BucketOperation> getOperation(int id);
    CompletableFuture<List<Integer>> getOpenedOperationsId();
}
