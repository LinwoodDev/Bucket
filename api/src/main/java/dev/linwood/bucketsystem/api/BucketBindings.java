package dev.linwood.bucketsystem.api;

import dev.linwood.bucketsystem.api.operations.BucketOperation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BucketBindings {
    @Nullable BucketOperation getOperation(int id);
    List<Integer> getOpenedOperationsId();
}
