package pl.org.sbolimowski.async.core;

import org.jvnet.hk2.annotations.Service;
import pl.org.sbolimowski.async.model.FacebookUser;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.Future;

@Service
public class FacebookService {

    private final WebTarget target = ClientBuilder.newClient()
            .target("http://graph.facebook.com/");

    public Future<FacebookUser> userAsync(String user) {
        return target
                .path("/{user}")
                .resolveTemplate("user", user)
                .request()
                .async()
                .get(new InvocationCallback<FacebookUser>() {
                    @Override
                    public void completed(FacebookUser facebookUser) {

                    }

                    @Override
                    public void failed(Throwable throwable) {

                    }
                });
    }
}
