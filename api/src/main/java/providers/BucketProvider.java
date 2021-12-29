package providers;

import dev.linwood.bucketsystem.api.BucketBindings;
import dev.linwood.bucketsystem.api.operations.BucketOperation;

import java.net.URL;

public class BucketProvider {
    public static BucketBindings getProviderByURL(URL url, String token, String forceProvider) {
        if (forceProvider != null) {
            return getProviderByName(forceProvider, url, token);
        }
        if (url.getHost().contains("github.com")) {
            return getProviderByName("GitHub", url, token);
        } else if (url.getHost().contains("gitlab.com")) {
            return getProviderByName("GitLab", url, token);
        } else {
            return null;
        }
    }

    private static BucketBindings getProviderByName(String name, URL url, String token) {
        try {
            return (BucketBindings) Class.forName("dev.linwood.bucketsystem.providers." + name + "Bindings").getConstructor(URL.class, String.class).newInstance(url, token);
        } catch (Exception e) {
            return null;
        }
    }
}
