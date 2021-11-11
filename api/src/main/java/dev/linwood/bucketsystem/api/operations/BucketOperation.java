package dev.linwood.bucketsystem.api.operations;

import dev.linwood.bucketsystem.api.Bucket;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public abstract class BucketOperation {
    public static final Pattern DATA_PATTERN = Pattern.compile("(<!---)( LINWOOD_BUCKET_DATA )(?<data>.+)(-->)");
    private final int id;
    private final boolean approved;

    public BucketOperation(int id, boolean approved) {
        this.id = id;
        this.approved = approved;
    }

    protected String getDataFromBody(String body) {
        var matcher = DATA_PATTERN.matcher(body);
        if (matcher.matches())
            return matcher.group("data");
        return null;
    }

    public static BucketOperation getOperationByBody(int id, String body, boolean approved) {
        var matcher = DATA_PATTERN.matcher(body);
        if (matcher.matches()) {
            var name = matcher.group("data");
            return getOperationByName(id, name, body, approved);
        }
        return null;
    }

    public static BucketOperation getOperationByName(int id, String name, String body, boolean approved) {
        return switch (name.toLowerCase().replace('_', '-')) {
            case "assetcreate", "asset-create" -> new AssetCreateOperation(body, id, approved);
            case "assetremove", "asset-remove" -> new AssetRemoveOperation(body, id, approved);
            case "assetedit", "asset-edit" -> new AssetEditOperation(body, id, approved);
            default -> null;
        };
    }

    public boolean isApproved() {
        return approved;
    }

    public int getId() {
        return id;
    }

    public abstract boolean apply(Bucket bucket);
}
