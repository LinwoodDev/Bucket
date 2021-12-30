package dev.linwood.bucketsystem.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class BucketAsset implements IdentifiableObject {
    private final Bucket parent;
    private final int id;
    private final Set<BucketUser> maintainers = new HashSet<>();
    private final BucketUser owner;
    private @NotNull String name, description = "", slug;

    BucketAsset(Bucket parent, @NotNull BucketUser owner, @NotNull String slug, int id) {
        this.id = id;
        this.owner = owner;
        this.slug = slug;
        name = slug;
        this.parent = parent;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public @NotNull String getSlug() {
        return slug;
    }

    public Set<BucketUser> getMaintainers() {
        return Collections.unmodifiableSet(maintainers);
    }

    public boolean registerMaintainer(BucketUser user) {
        return registerMaintainer(user.getSlug());
    }

    public boolean registerMaintainer(String identifier) {
        return maintainers.add(parent.getUser(identifier));
    }

    public boolean unregisterMaintainer(BucketUser user) {
        return unregisterMaintainer(user.getSlug());
    }

    public boolean unregisterMaintainer(String identifier) {
        return maintainers.remove(parent.getUser(identifier));
    }


    public List<String> getMaintainersIdentifier() {
        return maintainers.stream().map(BucketUser::getSlug).toList();
    }


    @Override
    public int getId() {
        return id;
    }

    public BucketUser getOwner() {
        return owner;
    }

    public String getOwnerIdentifier() {
        return owner.getSlug();
    }

    public Bucket getParent() {
        return parent;
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
        if (parent.getAsset(slug) != null)
            return false;
        this.slug = slug;
        return true;
    }
}
