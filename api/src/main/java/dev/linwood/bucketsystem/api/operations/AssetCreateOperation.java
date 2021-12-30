package dev.linwood.bucketsystem.api.operations;

import com.google.gson.JsonElement;
import dev.linwood.bucketsystem.api.Bucket;

public final class AssetCreateOperation extends BucketOperation {
    private final String slug, name, description;

    public AssetCreateOperation(JsonElement content, String user, int id, BucketOperationStatus status) {
        super(id, user, status);
        slug = "";
        name = "";
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
        var asset = bucket.registerAsset(getUser(), slug, getId());
        if (asset == null)
            return false;
        asset.setDescription(description);
        asset.setName(name);
        return true;
    }
}
