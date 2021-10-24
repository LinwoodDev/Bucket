package dev.linwood.bucketsystem.api;

import dev.linwood.bucketsystem.api.operations.BucketOperation;

public interface BucketBindings {
    BucketOperation getOperation(int id);
}
