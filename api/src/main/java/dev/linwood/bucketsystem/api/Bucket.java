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
    public BucketAsset registerAsset(String identifier) {
        var asset = new BucketAsset(this, identifier);
        if(!assets.add(asset))
            return null;
        return asset;
    }

    @Nullable
    public BucketAsset getAsset(String identifier) {
        return assets.stream().filter(bucketAsset -> bucketAsset.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }

    public boolean renameAsset(String identifier, String newIdentifier) {
        var asset = assets.stream().filter(bucketAsset -> bucketAsset.getIdentifier().equals(identifier)).findFirst().orElse(null);
        if(asset == null)return false;
        asset.setIdentifier(newIdentifier);
        return true;
    }

    public boolean unregisterAsset(String identifier) {
        return assets.removeIf(bucketAsset -> bucketAsset.getIdentifier().equals(identifier));
    }

    public Set<BucketAsset> getAssets() {
        return Collections.unmodifiableSet(assets);
    }

    @Nullable
    public BucketUser registerUser(String identifier) {
        var asset = new BucketUser(this, identifier);
        if(!users.add(asset))
            return null;
        return asset;
    }

    @Nullable
    public BucketUser getUser(String identifier) {
        return users.stream().filter(bucketUser -> bucketUser.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }

    public boolean unregisterUser(String identifier) {
        return users.removeIf(bucketUser -> bucketUser.getIdentifier().equals(identifier));
    }

    public Set<BucketUser> getUsers() {
        return Collections.unmodifiableSet(users);
    }
}
