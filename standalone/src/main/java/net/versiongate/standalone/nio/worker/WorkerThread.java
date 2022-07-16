package net.versiongate.standalone.nio.worker;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class WorkerThread extends Thread {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private WorkerThread(Runnable runnable) {
        super(null, runnable, "worker-" + COUNTER.getAndIncrement());
    }

    protected static void start(Consumer<WorkerContext> runnable) {
        new WorkerThread(() -> {
            final WorkerContext context = new WorkerContext();
            while (true) {
                try {
                    runnable.accept(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}