import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import pl.org.sbolimowski.async.utils.TaskExecutor;
import pl.org.sbolimowski.async.core.FacebookService;
import pl.org.sbolimowski.async.core.GitHubService;

import java.io.IOException;
import java.net.URI;


public class App {

    private static final URI BASE_URI = URI.create("http://localhost:8080/");

    public static void main(String[] args) throws IOException {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, create());
        System.in.read();
        server.shutdownNow();
    }

    public static ResourceConfig create() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("pl.org.sbolimowski.async.api");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(FacebookService.class).to(FacebookService.class);
                bind(GitHubService.class).to(GitHubService.class);
                bind(TaskExecutor.class).to(TaskExecutor.class);
            }
        });
        return resourceConfig;
    }

}