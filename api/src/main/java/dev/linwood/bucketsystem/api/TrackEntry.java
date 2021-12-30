package dev.linwood.bucketsystem.api;

import java.time.LocalDateTime;

public class TrackEntry {
    private final AssetTrack parent;
    private final LocalDateTime created;
    private String slug, name, description = "", link;
    private LocalDateTime updated;

    TrackEntry(AssetTrack parent, String slug, String link) {
        this.parent = parent;
        this.slug = slug;
        name = slug;
        this.link = link;
        this.created = LocalDateTime.now();
    }

    public AssetTrack getParent() {
        return parent;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
