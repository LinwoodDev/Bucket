package dev.linwood.bucketsystem.api;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AssetTrack {
    private final BucketAsset parent;
    private String name, slug, description = "";
    @Nullable
    private String link;
    private final Set<TrackEntry> entries = new HashSet<>();

    public AssetTrack(BucketAsset parent, String slug) {
        this.parent = parent;
        this.slug = slug;
        name = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BucketAsset getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public @Nullable String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(@Nullable String link) {
        this.link = link;
    }

    public Set<TrackEntry> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    public TrackEntry registerEntry(String slug) {
        var entry = new TrackEntry(this, slug);
        if(!entries.add(entry))
            return null;
        return entry;
    }

}
