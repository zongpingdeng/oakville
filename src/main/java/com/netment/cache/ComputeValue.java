package com.netment.cache;

/**
 * Created by jeff on 16/8/4.
 */
public interface ComputeValue<K , V> {

    V compute(K key);

}
