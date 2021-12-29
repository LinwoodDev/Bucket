package providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.linwood.bucketsystem.api.BucketBindings;
import dev.linwood.bucketsystem.api.operations.BucketOperation;

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
    private final String token;
    private final HttpClient httpClient;
    private static final Gson GSON = new GsonBuilder().create();
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
            return BucketOperation.getOperationByBody(
                    issue.get("id").getAsInt(),
                    issue.get("body").getAsString(),
                    issue.getAsJsonArray("labels").contains(new JsonPrimitive("approved")));
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
        }
        catch (URISyntaxException e) {
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
}
