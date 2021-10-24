package dev.linwood.bucketsystem.api;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class Bucket {
    private final Set<BucketAsset> assets = new HashSet<>();
    private final Set<BucketUser> users = new HashSet<>();

    @Nullable
    public BucketAsset registerAsset(String slug, int id) {
        var asset = new BucketAsset(this, slug, id);
        if(!assets.add(asset))
            return null;
        return asset;
    }

    @Nullable
    public BucketAsset getAsset(String slug) {
        return assets.stream().filter(bucketAsset -> bucketAsset.getSlug().equals(slug)).findFirst().orElse(null);
    }

    @Nullable
    public BucketAsset getAsset(int id) {
        return assets.stream().filter(bucketAsset -> bucketAsset.getId() == id).findFirst().orElse(null);
    }

    public boolean unregisterAsset(String slug) {
        return assets.removeIf(bucketAsset -> bucketAsset.getSlug().equals(slug));
    }

    public Set<BucketAsset> getAssets() {
        return Collections.unmodifiableSet(assets);
    }

    @Nullable
    public BucketUser registerUser(String slug, int id) {
        var asset = new BucketUser(this, slug, id);
        if(!users.add(asset))
            return null;
        return asset;
    }

    @Nullable
    public BucketUser getUser(String slug) {
        return users.stream().filter(bucketUser -> bucketUser.getSlug().equals(slug)).findFirst().orElse(null);
    }

    @Nullable
    public BucketUser getUser(int id) {
        return users.stream().filter(bucketUser -> bucketUser.getId() == id).findFirst().orElse(null);
    }

    public boolean unregisterUser(String slug) {
        return users.removeIf(bucketUser -> bucketUser.getSlug().equals(slug));
    }

    public Set<BucketUser> getUsers() {
        return Collections.unmodifiableSet(users);
    }
}
