package dev.linwood.bucketsystem.api;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class BucketAsset implements IdentifiableObject {
    private final Bucket parent;
    private String name, description, identifier;
    private List<BucketUser> maintainers;
    private List<BucketUser> owners;

    BucketAsset(Bucket parent, String identifier) {
        this.identifier = identifier;
        this.parent = parent;
    }

    BucketAsset(BucketAsset old, String newIdentifier) {
        this(old.getParent(), newIdentifier);
        name = old.name;
        description = old.description;
        maintainers = List.copyOf(old.maintainers);
        owners = List.copyOf(old.owners);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<BucketUser> getMaintainers() {
        return Collections.unmodifiableList(maintainers);
    }

    public List<String> getMaintainersIdentifier() {
        return maintainers.stream().map(BucketUser::getIdentifier).toList();
    }

    public List<BucketUser> getOwners() {
        return Collections.unmodifiableList(owners);
    }

    public List<String> getOwnersIdentifier() {
        return owners.stream().map(BucketUser::getIdentifier).toList();
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
        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
