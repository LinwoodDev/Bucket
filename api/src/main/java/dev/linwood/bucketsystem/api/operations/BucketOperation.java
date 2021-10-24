package dev.linwood.bucketsystem.api.operations;

import dev.linwood.bucketsystem.api.Bucket;

public abstract class BucketOperation {
    private final int id;
    private final boolean approved;

    public BucketOperation(int id, boolean approved) {
        this.id = id;
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public int getId() {
        return id;
    }

    public abstract void apply(Bucket bucket);
}
