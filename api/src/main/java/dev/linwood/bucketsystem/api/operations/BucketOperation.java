package dev.linwood.bucketsystem.api.operations;

import dev.linwood.bucketsystem.api.Bucket;

@SuppressWarnings("unused")
public abstract class BucketOperation {
    private final int id;
    private final boolean approved;

    public BucketOperation(int id, boolean approved) {
        this.id = id;
        this.approved = approved;
    }

    public static BucketOperation getOperationByName(int id, String name, String body, boolean approved) {
        return switch (name) {
            case "asset-create" -> new AssetCreateOperation(body, id, approved);
            case "asset-remove" -> new AssetRemoveOperation(body, id, approved);
            case "asset-edit" -> new AssetEditOperation(body, id, approved);
            default -> null;
        };
    }

    public boolean isApproved() {
        return approved;
    }

    public int getId() {
        return id;
    }

    public abstract boolean apply(Bucket bucket);
}
