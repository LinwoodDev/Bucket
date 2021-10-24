package dev.linwood.bucketsystem.api;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class BucketAsset implements IdentifiableObject {
    private final Bucket parent;
    private final int id;
    private String name, description, slug;
    private List<BucketUser> maintainers;
    private List<BucketUser> owners;

    BucketAsset(Bucket parent, String slug, int id) {
        this.id = id;
        this.slug = slug;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
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

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
