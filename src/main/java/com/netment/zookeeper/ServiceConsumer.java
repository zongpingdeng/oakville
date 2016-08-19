package com.netment.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jeff on 16/8/2.
 */
public class ServiceConsumer {

    private static final Logger log = Logger.getLogger(ServiceConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private volatile List<String> urlList = new ArrayList<String>();

    public <T extends Remote> T lookup(){
        T service = null;
        int size = this.urlList.size();
        if (size > 0){
            String url = null;
            if (size == 1){
                url = this.urlList.get(0);
            }
            else {
                url = this.urlList.get(ThreadLocalRandom.current().nextInt(size));
            }
            service = this.lookupService(url);
        }
        return service;
    }

    private <T> T lookupService(String url){
        T remote = null;
        try{
            remote = (T) Naming.lookup(url);
        }
        catch (Exception e){
            log.error("fail to lookup service " , e);
        }
        return remote;
    }

    private ZooKeeper connectZookeeperServer(){
        ZooKeeper zk = null;
        try{
            zk = new ZooKeeper(Constants.ZK_CONNECTION_STRING, Constants.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected){
                        latch.countDown();
                    }
                }
            });
        }
        catch (IOException e){
            log.error("Error while trying to connect to server " , e);
        }
        return zk;
    }

    private void watchNode(final ZooKeeper zk){
        try{
            List<String> nodeList = zk.getChildren(Constants.ZK_REGISTRY_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged){
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList = new ArrayList<String>();
            for (String node : nodeList) {
                byte[] data = zk.getData(Constants.ZK_REGISTRY_PATH + "/"+node , false , null);
                dataList.add(new String(data));
            }
            this.urlList = dataList;
        }
        catch (KeeperException e){
            log.error("error connecting zookeeper ", e);
        }
        catch (InterruptedException e){
            log.error("interrupterd " , e);
        }

    }
}
