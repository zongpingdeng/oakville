package com.netment.container;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jeff on 16/5/27.
 */
public class RenderThread extends Thread {

    private static final String DEFAULT_NAME = "render_worker_thread";
    private static final AtomicInteger threadCreated = new AtomicInteger(0);
    private static final AtomicInteger threadAlive = new AtomicInteger(0);
    private static final Logger logger = Logger.getLogger(RenderThread.class);

    public RenderThread(Runnable target) {
        this(target , DEFAULT_NAME);
    }

    public RenderThread(Runnable target, String name) {
        super(target, name + "_"+ threadCreated.incrementAndGet());
    }

    @Override
    public void run() {
        try{
            threadAlive.incrementAndGet();
            super.run();
        }
        finally {
            threadAlive.decrementAndGet();
        }

    }



    public static AtomicInteger getThreadAlive() {
        return threadAlive;
    }

    public static AtomicInteger getThreadCreated() {
        return threadCreated;
    }
}
