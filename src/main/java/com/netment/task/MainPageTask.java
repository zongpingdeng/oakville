package com.netment.task;

import com.netment.container.GoFireHere;
import com.netment.container.PageRender;
import org.apache.log4j.Logger;


/**
 * Created by jeff on 16/5/27.
 */
public class MainPageTask implements PageRender {

    private static final Logger logger = Logger.getLogger(MainPageTask.class);

    public MainPageTask(){
        GoFireHere l = new GoFireHere();
        logger.info("oooo"+l.getClass().getClassLoader());
        logger.info("I am here logger");

    }

    @Override
    public void render() throws Exception {
        logger.error("ddddddd");
        logger.info("I in main page"+this.getClass().getClassLoader().toString());
        logger.info("Current thread class loader is : ["+Thread.currentThread().getContextClassLoader().toString()+"]");
    }
}
