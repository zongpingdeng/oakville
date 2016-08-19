package com.netment.container;

/**
 * Created by jeff on 16/5/28.
 * This class must loaded by container class loader !!
 */
public class RenderTask implements Runnable{

    private String className;

    public RenderTask(String className) {
        this.className = className;
    }



    @Override
    public void run() {
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            PageRender render = (PageRender) Class.forName(this.className).newInstance();
            render.render();
            Thread.currentThread().setContextClassLoader(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
