package providers;

import com.google.gson.*;
import dev.linwood.bucketsystem.api.BucketBindings;
import dev.linwood.bucketsystem.api.operations.BucketOperation;

import java.net.URI;
import java.net.URL;
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

    public String getPath() {
        var path = repositoryURL.getPath().substring(1);
        if (path.endsWith(".git"))
            path = path.substring(0, path.length() - 4);
        return path;
    }

    private String getProjectId() {
        // Return URL encoded getPath
        return getPath().replace("/", "%2F");
    }

    @Override
    public CompletableFuture<BucketOperation> getOperation(int id) {
        // Get project id and then get issue body
        URI uri = URI.create(
                "https://" + getHost() + "/api/v4/projects/" + getProjectId() + "/issues/" + id
        );
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
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public CompletableFuture<List<Integer>> getOpenedOperationsId() {
        // Get issue ids
        URI uri = URI.create(
                "https://" + getHost() + "/api/v4/projects/" + getProjectId() + "/issues?state=opened"
        );
        var request = HttpRequest.newBuilder(uri).header("Authorization", "Bearer " + token)
                .header("accept", "application/json").build();
        var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(stringHttpResponse -> {
            HttpResponse<String> value;
            try {
                value = response.get();
                var body = value.body();
                var jsonArray = GSON.fromJson(body, JsonArray.class);
                return StreamSupport.stream(jsonArray.spliterator(), false).map(jsonElement -> jsonElement.getAsJsonObject().get("iid").getAsInt()).toList();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
