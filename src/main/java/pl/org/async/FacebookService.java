package pl.org.async;

import org.jvnet.hk2.annotations.Service;
import pl.org.async.model.FacebookInfo;

import javax.ws.rs.client.ClientBuilder;
import java.util.concurrent.Future;

@Service
public class FacebookService {

    public Future<FacebookInfo> getInfoAsync(String user) {
        return ClientBuilder.newClient()
                .target("http://graph.facebook.com/")
                .path("/{user}").resolveTemplate("user", user)
                .request().async()
                .get(FacebookInfo.class);
    }
}
