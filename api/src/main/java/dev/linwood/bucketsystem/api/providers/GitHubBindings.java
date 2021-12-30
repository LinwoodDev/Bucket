package dev.linwood.bucketsystem.api.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.linwood.bucketsystem.api.BucketBindings;
import dev.linwood.bucketsystem.api.operations.BucketOperation;
import dev.linwood.bucketsystem.api.operations.BucketOperationStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

public class GitHubBindings implements BucketBindings {
    private static final Gson GSON = new GsonBuilder().create();
    private final String token;
    private final HttpClient httpClient;
    private final URL repositoryURL;

    public GitHubBindings(URL repositoryURL, String token) {
        this.repositoryURL = repositoryURL;
        this.token = token;
        httpClient = HttpClient.newHttpClient();
    }


    @Override
    public CompletableFuture<BucketOperation> getOperation(int id) {
        URI uri = null;
        try {
            uri = new URI(
                    "https",
                    "api." + repositoryURL.getHost(),
                    "/repos/" + repositoryURL.getPath() + "/issues/",
                    null
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert uri != null;
        var request = HttpRequest.newBuilder(uri).header("Authorization", "Bearer " + token)
                .header("accept", "application/json").build();
        var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(httpResponse -> {
            var body = httpResponse.body();
            var issue = GSON.fromJson(body, JsonObject.class);
            var labels = issue.getAsJsonArray("labels");
            return BucketOperation.getOperationByBody(
                    issue.get("id").getAsInt(),
                    issue.getAsJsonObject("user").get("login").getAsString(),
                    issue.get("body").getAsString(),
                    BucketOperationStatus.fromJsonArray(labels));
        });
    }

    @Override
    public CompletableFuture<List<Integer>> getOpenedOperationsId() {

        // Get issue ids
        URI uri = null;
        try {
            uri = new URI(
                    "https",
                    "api." + repositoryURL.getHost(),
                    "/repos/" + repositoryURL.getPath() + "/issues",
                    null
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert uri != null;
        var request = HttpRequest.newBuilder(uri).header("Authorization", "Bearer " + token)
                .header("accept", "application/json").build();
        var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(httpResponse -> {
            var body = httpResponse.body();
            var issues = GSON.fromJson(body, JsonObject.class);
            var ids = StreamSupport.stream(issues.getAsJsonArray("issues").spliterator(), false).map(issue -> issue.getAsJsonObject().get("id").getAsInt());
            return ids.toList();
        });
    }


    @Override
    public CompletableFuture<List<Integer>> getOpenedOperationsId(BucketOperationStatus status) {

        // Get issue ids
        URI uri = null;
        try {
            uri = new URI(
                    "https",
                    "api." + repositoryURL.getHost(),
                    "/repos/" + repositoryURL.getPath() + "/issues",
                    null
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert uri != null;
        var request = HttpRequest.newBuilder(uri).header("Authorization", "Bearer " + token)
                .header("accept", "application/json").build();
        var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(httpResponse -> {
            var body = httpResponse.body();
            var issues = GSON.fromJson(body, JsonObject.class);
            var ids = StreamSupport.stream(issues.getAsJsonArray("issues").spliterator(), false).map(issue -> issue.getAsJsonObject().get("id").getAsInt());
            return ids.toList();
        });
    }

    @Override
    public CompletableFuture<BucketOperationStatus> getOperationStatus(int id) {
        return null;
    }

    @Override
    public CompletableFuture<Void> setOperationStatus(int id, BucketOperationStatus status) {
        return null;
    }
}
