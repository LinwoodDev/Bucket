package dev.linwood.bucketsystem.gitlab;

import dev.linwood.bucketsystem.api.BucketBindings;
import dev.linwood.bucketsystem.api.operations.BucketOperation;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GitLabBindings implements BucketBindings {
    private final String token;
    private final HttpClient httpClient;

    public GitLabBindings(String token) {
        this.token = token;
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public BucketOperation getOperation(int id) {
        var rest = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder()
                .header("accept", "application/json").build();
        var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            var value = response.get();
            var body = value.body();
            return BucketOperation.getOperationByName(id, body, body, true);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Integer> getOpenedOperationsId() {
        return null;
    }
}
