package dev.linwood.bucketsystem.api;

import java.time.LocalDateTime;

public class TrackEntry {
    private final AssetTrack parent;
    private String slug, name, description = "";
    private final LocalDateTime created;
    private LocalDateTime updated;

    TrackEntry(AssetTrack parent, String slug) {
        this.parent = parent;
        this.slug = slug;
        name = slug;
        this.created = LocalDateTime.now();
    }

    public AssetTrack getParent() {
        return parent;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
