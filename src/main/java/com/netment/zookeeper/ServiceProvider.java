package com.netment.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jeff on 16/8/2.
 */
public class ServiceProvider {

    private static final Logger log = Logger.getLogger(ServiceProvider.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public void publish(Remote service, String host, int port) {
        String url = publishService(service, host, port);
        if (url != null) {
            ZooKeeper zk = connectZookeeperServer();
            if (zk != null){
                createZookeeperNode(zk , url);
            }
        }
    }

    public String publishService(Remote service, String host, int port) {
        String url = null;
        url = String.format("rmi://%s:%d/%s", host, port, service.getClass().getName());
        try {
            LocateRegistry.createRegistry(port);
            Naming.rebind(url, service);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    private ZooKeeper connectZookeeperServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(Constants.ZK_CONNECTION_STRING, Constants.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            log.error("Can not create connection to zookeeper server", e);
        } catch (InterruptedException e) {
            log.error("Error waking up ", e);
        }
        return zk;
    }


    private void createZookeeperNode(ZooKeeper zk, String url) {
        try {
            byte[] data = url.getBytes();
            String path = zk.create(Constants.ZK_PROVIDER_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        } catch (KeeperException e) {

        } catch (InterruptedException e) {

        }
    }


}
