package com.netment.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created by jeff on 16/8/1.
 */
public class HelloRmiServer {

    public static void main(String[] args) throws Exception{
        int port = 1099;
        String url = "rmi://localhost:1099/demo.zookeeper.remoting.server.HelloServiceImpl";
        LocateRegistry.createRegistry(port);
        Naming.rebind(url, new HelloServiceImpl());
    }
}
