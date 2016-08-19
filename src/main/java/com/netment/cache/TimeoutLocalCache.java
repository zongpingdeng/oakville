package com.netment.cache;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by jeff on 16/8/4.
 */
public class TimeoutLocalCache<K , V> {


    private static final Logger log = Logger.getLogger(TimeoutLocalCache.class);

    private final ConcurrentMap<K , TimeoutTask<V>> cache = new ConcurrentHashMap<K , TimeoutTask<V>>();

    private final ComputeValue<K , V> c;

    private final long cacheInterval ;

    private final Runnable refreshTask = new Runnable() {
        @Override
        public void run() {
                Set<K> keys = cache.keySet();
                Iterator<K> keyIt = keys.iterator();
                while (keyIt.hasNext()){
                    K key = keyIt.next();
                    TimeoutTask<V> t = cache.get(key);
                    if (t != null){
                        if (System.currentTimeMillis() - t.getTimestamp() > cacheInterval){
                            cache.remove(key , t);
                        }
                    }
                }
            }
    };

    public TimeoutLocalCache(ComputeValue<K , V> c , long cacheInterval) {
        this.c = c;
        this.cacheInterval = cacheInterval;
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(refreshTask, 1000 , 1000 , TimeUnit.SECONDS);
    }




    public V compute(final K key) throws InterruptedException{
        while (true){
            TimeoutTask<V> f = cache.get(key);
            if (f == null){//Cache not hit
                Callable<V> call = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(key);
                    }
                };

                TimeoutTask<V> ft = new TimeoutTask<V>(call , System.currentTimeMillis());
                f = cache.putIfAbsent(key , ft);
                if (f == null){//No mapping in cache, is the first task, should compute value
                    f = ft; ft.run();
                }
            }
            try{
                return f.get();
            }
            catch (CancellationException e){
                cache.remove(key , f);
            }
            catch (ExecutionException e){
              log.error("Error",e);
            }

        }
    }




}
