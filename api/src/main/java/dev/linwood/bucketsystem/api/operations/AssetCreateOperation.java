package dev.linwood.bucketsystem.api.operations;

import dev.linwood.bucketsystem.api.Bucket;

public final class AssetCreateOperation extends BucketOperation {
    private final String slug, name, description;

    public AssetCreateOperation(String body, int id, boolean approved) {
        super(id, approved);
        slug = ""; name = "";
        description = "";
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean apply(Bucket bucket) {
        var asset = bucket.registerAsset(slug, getId());
        if(asset == null)
            return false;
        asset.setDescription(description);
        asset.setName(name);
        return true;
    }
}
