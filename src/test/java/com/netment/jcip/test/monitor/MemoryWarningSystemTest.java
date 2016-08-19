package com.netment.jcip.test.monitor;

import com.netment.monitor.MemoryWarningSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 16/8/15.
 */
public class MemoryWarningSystemTest {

    public static void main(String[] args) throws Exception{
        MemoryWarningSystem.setPercentageUsageThreshold(0.6);
        MemoryWarningSystem mws = new MemoryWarningSystem();
        mws.addMemoryListener(new MemoryWarningSystem.MemoryWarningListener() {
            @Override
            public void memoryUsage(long usedMemory, long maxMemory) {
                System.out.println("Memory low " + usedMemory + "|" + maxMemory);
            }
        });

        List<Double> list = new ArrayList<>();
        while (true){
            list.add(new Double(Math.random()));
        }

    }
}
