package dev.linwood.bucketsystem.api;

import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("unused")
public class BucketUser implements IdentifiableObject {
    private String name, bio = "", slug;
    @Nullable
    private String website, twitter;
    private final int id;
    private final Bucket parent;
    private final Set<BucketAsset> subscribedAssets = new HashSet<>();

    BucketUser(Bucket parent, String slug, int id) {
        this.slug = slug;
        this.name = slug;
        this.parent = parent;
        this.id = id;
    }

    public Bucket getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public int getId() {
        return id;
    }

    public @Nullable String getTwitter() {
        return twitter;
    }

    public @Nullable String getWebsite() {
        return website;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTwitter(@Nullable String twitter) {
        this.twitter = twitter;
    }

    public void setWebsite(@Nullable String website) {
        this.website = website;
    }

    public List<BucketAsset> getOwningAssets() {
        return parent.getAssets().stream().filter(bucketAsset -> bucketAsset.getOwners().contains(this)).toList();
    }

    public List<BucketAsset> getMaintainedAssets() {
        return parent.getAssets().stream().filter(bucketAsset -> bucketAsset.getMaintainers().contains(this)).toList();
    }

    public Set<BucketAsset> getSubscribedAssets() {
        return Collections.unmodifiableSet(subscribedAssets);
    }

    public boolean subscribe(BucketAsset asset) {
        return subscribe(asset.getSlug());
    }

    public boolean subscribe(String identifier) {
        var asset = parent.getAsset(identifier);
        if (asset != null)
            return subscribedAssets.add(asset);
        return false;
    }

    public boolean unsubscribe(BucketAsset asset) {
        return unsubscribe(asset.getSlug());
    }

    public boolean unsubscribe(String identifier) {
        var asset = parent.getAsset(identifier);
        if (asset != null)
            return subscribedAssets.remove(asset);
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BucketUser that = (BucketUser) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
