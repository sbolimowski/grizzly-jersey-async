package pl.org.sbolimowski.async.core;

import org.jvnet.hk2.annotations.Service;
import pl.org.sbolimowski.async.model.FacebookInfo;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.Future;

@Service
public class FacebookService {

    private final WebTarget webTarget = ClientBuilder.newClient()
            .target("http://graph.facebook.com/")
            .path("/{user}");

    public Future<FacebookInfo> getInfoAsync(String user) {
        return webTarget
                .resolveTemplate("user", user)
                .request()
                .async()
                .get(FacebookInfo.class);
    }
}
