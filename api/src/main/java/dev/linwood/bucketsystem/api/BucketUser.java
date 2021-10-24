package dev.linwood.bucketsystem.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class BucketUser implements IdentifiableObject {
    private String name, bio, website, twitter;
    private final String identifier;
    private final Bucket parent;
    private final Set<BucketAsset> subscribedAssets = new HashSet<>();

    BucketUser(Bucket parent, String identifier) {
        this.parent = parent;
        this.identifier = identifier;
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

    public String getIdentifier() {
        return identifier;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getWebsite() {
        return website;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setWebsite(String website) {
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
        return subscribe(asset.getIdentifier());
    }

    public boolean subscribe(String identifier) {
        var asset = parent.getAsset(identifier);
        if (asset != null)
            return subscribedAssets.add(asset);
        return false;
    }

    public boolean unsubscribe(BucketAsset asset) {
        return unsubscribe(asset.getIdentifier());
    }

    public boolean unsubscribe(String identifier) {
        var asset = parent.getAsset(identifier);
        if (asset != null)
            return subscribedAssets.remove(asset);
        return false;
    }
}
