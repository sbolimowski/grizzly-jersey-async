package pl.org.async;

import org.jvnet.hk2.annotations.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
public class TaskExecutor implements Executor {

    private final Executor delegate = new ScheduledThreadPoolExecutor(4);

    @Override
    public void execute(Runnable command) {
        delegate.execute(command);
    }
}
