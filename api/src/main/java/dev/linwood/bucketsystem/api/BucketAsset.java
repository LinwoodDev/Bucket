package dev.linwood.bucketsystem.api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class BucketAsset implements IdentifiableObject {
    private final Bucket parent;
    private final int id;
    private @NotNull String name, description = "", slug;
    private final List<BucketUser> maintainers = new ArrayList<>();
    private final List<BucketUser> owners = new ArrayList<>();

    BucketAsset(Bucket parent, @NotNull String slug, int id) {
        this.id = id;
        this.slug = slug;
        name = slug;
        this.parent = parent;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public @NotNull String getSlug() {
        return slug;
    }

    public List<BucketUser> getMaintainers() {
        return Collections.unmodifiableList(maintainers);
    }

    public List<String> getMaintainersIdentifier() {
        return maintainers.stream().map(BucketUser::getSlug).toList();
    }

    @Override
    public int getId() {
        return id;
    }

    public List<BucketUser> getOwners() {
        return Collections.unmodifiableList(owners);
    }

    public List<String> getOwnersIdentifier() {
        return owners.stream().map(BucketUser::getSlug).toList();
    }

    public Bucket getParent() {
        return parent;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BucketAsset that = (BucketAsset) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }

    public boolean setSlug(@NotNull String slug) {
        if(parent.getAsset(slug) != null)
            return false;
        this.slug = slug;
        return true;
    }
}
