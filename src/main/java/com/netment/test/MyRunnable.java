package com.netment.test;

import com.netment.container.PageRender;
import com.netment.task.MainPageTask;
import org.apache.log4j.Logger;

/**
 * Created by jeff on 16/5/28.
 */
public class MyRunnable implements Runnable {

    private static final Logger logger = Logger.getLogger(MyRunnable.class);

    @Override
    public void run() {
        try {
            PageRender pageRender = new MainPageTask();
            pageRender.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
