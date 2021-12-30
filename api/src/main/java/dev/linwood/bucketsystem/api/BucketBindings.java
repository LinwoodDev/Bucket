package dev.linwood.bucketsystem.api;

import dev.linwood.bucketsystem.api.operations.BucketOperation;
import dev.linwood.bucketsystem.api.operations.BucketOperationStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BucketBindings {
    CompletableFuture<@Nullable BucketOperation> getOperation(int id);

    CompletableFuture<List<Integer>> getOpenedOperationsId();

    CompletableFuture<List<Integer>> getOpenedOperationsId(BucketOperationStatus status);

    CompletableFuture<BucketOperationStatus> getOperationStatus(int id);

    CompletableFuture<Void> setOperationStatus(int id, BucketOperationStatus status);

}
