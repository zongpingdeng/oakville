package com.netment.container;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jeff on 16/5/28.
 */
public class RenderTaskFactory {

    private static RenderTaskFactory factory = null;

    private static final String CONFIG_FILE_NAME = "uri-mapping-config.properties";

    private static final String TASK_NAME = "com.netment.container.RenderTask";

    private static final Logger logger = Logger.getLogger(RenderTaskFactory.class);

    private static final ConcurrentHashMap<String, String> uriMapping = new ConcurrentHashMap<>();


    /* init here to avoid double check locking */
    static {
        RenderTaskFactory f = new RenderTaskFactory();
        f.init();
        factory = f;
    }

    public static RenderTaskFactory getInstance(){
        return factory;
    }


    public void init() {
       // Thread.currentThread().setContextClassLoader();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        Properties p = new Properties();
        try {
            p.load(is);
            Enumeration enums = p.propertyNames();
            while (enums.hasMoreElements()) {
                String key = enums.nextElement().toString();
                Object v = p.get(key);
                uriMapping.put(key, v.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  Runnable createRenderTask(String path) {
        try {
            ContainerAppClassLoader cls = new ContainerAppClassLoader();
            Class clazz = Class.forName(TASK_NAME , true , cls);
            Runnable task = (Runnable) clazz.getDeclaredConstructor(String.class).newInstance(this.getTaskClassNameByUriPath(path));
            return task;
        } catch (Exception e) {
            logger.error("Can not create task !!", e);
            return null;
        }
    }

    private  String getTaskClassNameByUriPath(String path) {
        String className = uriMapping.get(path);
        return className;
    }
}
