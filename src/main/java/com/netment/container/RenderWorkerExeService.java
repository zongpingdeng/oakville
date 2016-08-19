package com.netment.container;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by jeff on 16/5/28.
 */
public class RenderWorkerExeService {




    private static RenderWorkerExeService service = null;

    static{
        service = new RenderWorkerExeService();
    }

    public static RenderWorkerExeService getInstance(){
        return service;
    }

    private ExecutorService executorService = null;

    public RenderWorkerExeService() {

        this.executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new RenderThread(r);
            }
        }) ;
    }

    public void execute(String requestUri){
        Runnable task = RenderTaskFactory.getInstance().createRenderTask(requestUri);
        this.executorService.execute(task);
    }
}
