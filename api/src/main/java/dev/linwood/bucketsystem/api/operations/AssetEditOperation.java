package dev.linwood.bucketsystem.api.operations;

import dev.linwood.bucketsystem.api.Bucket;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public final class AssetEditOperation extends BucketOperation {
    private final @Nullable String name, description, slug;

    public AssetEditOperation(String body, int id, boolean approved) {
        super(id, approved);
        name = "";
        description = "";
        slug = "";
    }

    public @Nullable String getName() {
        return name;
    }

    public @Nullable String getSlug() {
        return slug;
    }

    public @Nullable String getDescription() {
        return description;
    }

    @Override
    public boolean apply(Bucket bucket) {
        if (name == null && slug == null && description == null)
            return false;
        var asset = bucket.getAsset(getId());
        if (asset == null)
            return false;
        if (name != null)
            asset.setName(name);
        if (description != null)
            asset.setDescription(description);
        if (slug != null)
            return asset.setSlug(slug);
        return true;
    }
}
