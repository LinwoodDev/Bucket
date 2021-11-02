package dev.linwood.bucketsystem.providers;

import com.google.gson.*;
import dev.linwood.bucketsystem.api.BucketBindings;
import dev.linwood.bucketsystem.api.operations.BucketOperation;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

public class GitLabBindings implements BucketBindings {
    private final String token;
    private final HttpClient httpClient;
    private static final Gson GSON = new GsonBuilder().create();
    private final URL repositoryURL;

    public GitLabBindings(URL repositoryURL, String token) {
        this.repositoryURL = repositoryURL;
        this.token = token;
        httpClient = HttpClient.newHttpClient();
    }

    public String getHost() {
        return repositoryURL.getHost();
    }

    private CompletableFuture<Integer> loadProjectId() {
        // Get host from repository URL
        URI uri = null;
        try {
            uri = new URI(
                    "https",
                    repositoryURL.getHost(),
                    "api/v4/projects/" + repositoryURL.getPath(),
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
                return jsonObject.get("id").getAsInt();
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public CompletableFuture<BucketOperation> getOperation(int id) {
        // Get project id and then get issue body
        return loadProjectId().thenCompose(projectId -> {
            URI uri = null;
            try {
                uri = new URI(
                        "https",
                        getHost(),
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
                    var bodyJson = jsonObject.get("description").getAsString();
                    var operation = GSON.fromJson(bodyJson, JsonObject.class);
                    var labels = operation.getAsJsonArray("labels");
                    return BucketOperation.getOperationByName(operation.get("id").getAsInt(),
                            operation.get("title").getAsString(),
                            operation.get("description").getAsString(),
                            labels.contains(new JsonPrimitive("approved")));
                }
                catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            });
        });
    }

    @Override
    public CompletableFuture<List<Integer>> getOpenedOperationsId() {
        // Get issue ids
        return loadProjectId().thenCompose(projectId -> {
            URI uri = null;
            try {
                uri = new URI(
                        "https",
                        getHost(),
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
                }
                catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            });
        });
    }
}
