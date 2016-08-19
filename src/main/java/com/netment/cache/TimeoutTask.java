package com.netment.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by jeff on 16/8/5.
 */
public class TimeoutTask<V> extends FutureTask<V> {

    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public TimeoutTask(Callable<V> call , long timestamp){
        super(call);
        this.timestamp = timestamp;
    }
}
