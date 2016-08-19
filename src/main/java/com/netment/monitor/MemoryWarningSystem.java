package com.netment.monitor;

import org.apache.log4j.Logger;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 16/8/14.
 */
public class MemoryWarningSystem {

    private static final Logger log = Logger.getLogger(MemoryWarningSystem.class);

    List<MemoryWarningListener> listeners = new ArrayList<>();

    private static final MemoryPoolMXBean tenurePool = findTenureGenPool();

    private static MemoryPoolMXBean findTenureGenPool(){
        List<MemoryPoolMXBean> memPool = ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean pool : memPool){
            String s1 = pool.getName();
            String[] s2 = pool.getMemoryManagerNames();
            MemoryUsage usage = pool.getUsage();
            if (pool.getType().equals(MemoryType.HEAP) && pool.isUsageThresholdSupported()){
                return pool;
            }
        }
        throw new  RuntimeException("Can not find heap memory pool");
    }



    public interface MemoryWarningListener {
        public void memoryUsage(long usedMemory , long maxMemory);
    }

    public void addMemoryListener(MemoryWarningListener listener){
        this.listeners.add(listener);
    }

    public void removeMemoryListener(MemoryWarningListener listener){
        this.listeners.remove(listener);
    }


    public MemoryWarningSystem(){
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String vmName = runtimeBean.getVmName();
        String vmVendor = runtimeBean.getVmVendor();
        String vmVersion = runtimeBean.getVmVersion();
        String specVersion = runtimeBean.getManagementSpecVersion();

        log.info(vmName + vmVendor + vmVersion + specVersion);

        System.out.println(runtimeBean.getVmName());
        List<String> args = runtimeBean.getInputArguments();
        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
        NotificationEmitter emitter = (NotificationEmitter) mbean;
        emitter.addNotificationListener(new NotificationListener() {
            @Override
            public void handleNotification(Notification notification, Object handback) {
                if (notification.getType().equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)){
                    long maxMemory = tenurePool.getUsage().getMax();
                    long usedMemory = tenurePool.getUsage().getUsed();
                    for (MemoryWarningListener listener : listeners){
                        listener.memoryUsage(usedMemory , maxMemory);
                    }
                }
            }
        }, null , null);
    }


    public static void setPercentageUsageThreshold(double percentage){
        if (percentage <= 0.0 || percentage >1.0){
            throw new IllegalArgumentException("Arg not in range");
        }
        long maxMemory = tenurePool.getUsage().getMax();
        long warningThreshold = (long) (maxMemory * percentage);
        tenurePool.setUsageThreshold(warningThreshold);
    }

    


}
