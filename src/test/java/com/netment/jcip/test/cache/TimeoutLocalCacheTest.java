package com.netment.jcip.test.cache;

import com.netment.cache.ComputeValue;
import com.netment.cache.TimeoutLocalCache;

import java.util.Random;

/**
 * Created by jeff on 16/8/5.
 */
public class TimeoutLocalCacheTest {

    public static void main(String[] args) throws Exception{
        TimeoutLocalCache<Integer , Long> localCache = new TimeoutLocalCache<Integer, Long>(new ComputeValue<Integer, Long>() {
            @Override
            public Long compute(Integer key) {
                try {
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 100l;
            }
        } , 10000);
        Random random = new Random();
        while (true){
            localCache.compute(random.nextInt());
        }
    }


}
