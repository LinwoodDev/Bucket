package dev.linwood.bucketsystem.api.operations;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Objects;
import java.util.stream.StreamSupport;

public enum BucketOperationStatus {
    ACCEPTED,
    VALID,
    INVALID,
    IN_PROGRESS;

    public static BucketOperationStatus fromString(String status) {
        status = status.toUpperCase();
        try {
            return valueOf(status);
        } catch (IllegalArgumentException e) {
            return IN_PROGRESS;
        }
    }

    public static BucketOperationStatus fromJsonArray(final JsonArray array) {
        return fromIterable(StreamSupport.stream(array.spliterator(), false).map(JsonElement::getAsString).toList());
    }

    public static BucketOperationStatus fromIterable(final Iterable<String> list) {
        var statusList = StreamSupport.stream(list.spliterator(), false).map(BucketOperationStatus::fromString).filter(Objects::nonNull).toList();
        if (statusList.contains(ACCEPTED)) {
            return ACCEPTED;
        } else if (statusList.contains(VALID)) {
            return VALID;
        } else if (statusList.contains(INVALID)) {
            return INVALID;
        }
        return IN_PROGRESS;
    }
}
