package dev.linwood.bucketsystem.api.operations;

import com.google.gson.JsonElement;
import dev.linwood.bucketsystem.api.Bucket;

public final class AssetRemoveOperation extends BucketOperation {
    private final int assetId;

    public AssetRemoveOperation(JsonElement content, String user, int id, BucketOperationStatus status) {
        super(id, user, status);
        this.assetId = 0;
    }

    @Override
    public boolean apply(Bucket bucket) {
        return bucket.unregisterAsset(assetId);
    }
}
