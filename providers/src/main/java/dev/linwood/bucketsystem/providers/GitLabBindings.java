package dev.linwood.bucketsystem.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.linwood.bucketsystem.api.BucketBindings;
import dev.linwood.bucketsystem.api.operations.BucketOperation;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

public class GitLabBindings implements BucketBindings {
    private final String token;
    private final HttpClient httpClient;
    private final int projectId;
    private static final Gson GSON = new GsonBuilder().create();
    private final String host;

    public GitLabBindings(String token, int projectId, String host) {
        this.token = token;
        this.host = host;
        this.projectId = projectId;
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public CompletableFuture<BucketOperation> getOperation(int id) {
        URI uri = null;
        try {
            uri = new URI(
                    "https",
                    host,
                    "api/v4/projects/" + projectId + "/issues/" + id,
                    null
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert uri != null;
        var request = HttpRequest.newBuilder(uri).header("Authorization", "Bearer " + token)
                .header("accept", "application/json").build();
        var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(stringHttpResponse -> {
            HttpResponse<String> value;
            try {
                value = response.get();
                var body = value.body();
                var jsonObject = GSON.fromJson(body, JsonObject.class);

                return BucketOperation.getOperationByName(id, jsonObject.get("title").getAsString(), jsonObject.get("description").getAsString(), true);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public CompletableFuture<List<Integer>> getOpenedOperationsId() {
        URI uri = null;
        try {
            uri = new URI(
                    "https",
                    host,
                    "api/v4/projects/" + projectId + "/issues?state=opened",
                    null
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert uri != null;
        var request = HttpRequest.newBuilder(uri).header("Authorization", "Bearer " + token)
                .header("accept", "application/json").build();
        var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(stringHttpResponse -> {
            HttpResponse<String> value;
            try {
                value = response.get();
                var body = value.body();
                var jsonArray = GSON.fromJson(body, JsonArray.class);
                return StreamSupport.stream(jsonArray.spliterator(), false).map(jsonElement -> jsonElement.getAsJsonObject().get("id").getAsInt()).toList();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
