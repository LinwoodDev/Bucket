package dev.linwood.bucketsystem.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BucketConfig {
    private static final Gson GSON = new GsonBuilder().create();
    private final String name;
    private final String description;

    public BucketConfig(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public BucketConfig() {
        name = "TestBucket";
        description = "This is a test bucket";
    }

    public BucketConfig(JsonObject json) {
        name = json.get("name").getAsString();
        description = json.get("description").getAsString();
    }

    public static BucketConfig load(Path path) throws IOException {
        var reader = Files.newBufferedReader(path);
        var config = new BucketConfig(GSON.fromJson(reader, JsonObject.class));
        reader.close();
        return config;
    }
}
