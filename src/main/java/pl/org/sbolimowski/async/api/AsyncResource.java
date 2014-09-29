package pl.org.sbolimowski.async.api;

import pl.org.sbolimowski.async.core.FacebookService;
import pl.org.sbolimowski.async.core.Futures;
import pl.org.sbolimowski.async.core.GitHubService;
import pl.org.sbolimowski.async.core.TaskExecutor;
import pl.org.sbolimowski.async.model.FacebookInfo;
import pl.org.sbolimowski.async.model.GitHubInfo;
import pl.org.sbolimowski.async.model.UserInfo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Path("/")
public class AsyncResource {

    @Inject
    private FacebookService facebookService;

    @Inject
    private GitHubService gitHubService;

    @Inject
    private TaskExecutor executor;

    @GET
    @Path("/async/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void userInfoAsync(@Suspended AsyncResponse asyncResponse, @PathParam("user") String user) {
        CompletableFuture<GitHubInfo> gitHubInfoFuture = Futures.toCompletable(gitHubService.getInfoAsync(user), executor);
        CompletableFuture<FacebookInfo> facebookInfoFuture = Futures.toCompletable(facebookService.getInfoAsync(user), executor);

        gitHubInfoFuture
                .thenCombine(
                        facebookInfoFuture, (g, f) -> new UserInfo(f, g))
                .thenApply(
                        info -> asyncResponse.resume(info))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

    }
}
