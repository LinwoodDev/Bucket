package dev.linwood.bucketsystem.api.operations;

import com.google.gson.*;
import dev.linwood.bucketsystem.api.Bucket;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public abstract class BucketOperation {
    public static final Pattern DATA_PATTERN = Pattern.compile("(<!---)( LINWOOD_BUCKET_DATA )(?<data>.+)(-->)");
    private static final Gson GSON = new GsonBuilder().create();
    private final int id;
    private final BucketOperationStatus status;
    private final String user;

    public BucketOperation(int id, String user, BucketOperationStatus status) {
        this.user = user;
        this.id = id;
        this.status = status;
    }

    public static BucketOperation getOperationByBody(int id, String body, String user, BucketOperationStatus status) {
        var matcher = DATA_PATTERN.matcher(body);
        if (matcher.matches()) {
            var name = matcher.group("data");
            try {
                var content = GSON.fromJson(name, JsonObject.class);
                return getOperationByName(id, content.get("name").getAsString(), user, content.get("content"), status);
            } catch (JsonSyntaxException e) {
                return null;
            }
        }
        return null;
    }

    public static BucketOperation getOperationByName(int id, String user, String name, JsonElement content, BucketOperationStatus status) {
        return switch (name.toLowerCase().replace("_", "").replace("-", "")) {
            case "assetcreate" -> new AssetCreateOperation(content, user, id, status);
            case "assetremove" -> new AssetRemoveOperation(content, user, id, status);
            case "assetedit" -> new AssetEditOperation(content, user, id, status);
            default -> null;
        };
    }

    protected JsonObject getDataFromBody(String body) {
        var matcher = DATA_PATTERN.matcher(body);
        if (matcher.matches()) {
            var data = matcher.group("data");
            try {
                return GSON.fromJson(data, JsonObject.class);
            } catch (JsonSyntaxException e) {
                return null;
            }
        }
        return null;
    }

    public BucketOperationStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public abstract boolean apply(Bucket bucket);
}
