package dev.linwood.bucketsystem.api.operations;

import dev.linwood.bucketsystem.api.Bucket;

public class AssetCreateOperation extends BucketOperation {
    private final String slug, name;

    public AssetCreateOperation(String body, int id, boolean approved) {
        super(id, approved);
        slug = ""; name = "";
    }

    @Override
    public void apply(Bucket bucket) {

    }
}
