package dev.linwood.bucketsystem.api.operations;

import dev.linwood.bucketsystem.api.Bucket;

public final class AssetRemoveOperation extends BucketOperation {
    private final int assetId;

    public AssetRemoveOperation(String body, int id, boolean approved) {
        super(id, approved);
        this.assetId = 0;
    }

    @Override
    public boolean apply(Bucket bucket) {
        return bucket.unregisterAsset(assetId);
    }
}
