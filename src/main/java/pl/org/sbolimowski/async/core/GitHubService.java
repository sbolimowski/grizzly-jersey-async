package pl.org.sbolimowski.async.core;

import org.jvnet.hk2.annotations.Service;
import pl.org.sbolimowski.async.model.GitHubInfo;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.Future;

@Service
public class GitHubService {

    private final WebTarget webTarget = ClientBuilder.newClient()
            .target("https://api.github.com/")
            .path("/users/{user}");

    public Future<GitHubInfo> getInfoAsync(String user) {
        return webTarget
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(GitHubInfo.class);
    }
}
