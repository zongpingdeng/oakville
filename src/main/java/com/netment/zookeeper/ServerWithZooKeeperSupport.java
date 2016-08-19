package com.netment.zookeeper;

import com.netment.rmi.HelloService;
import com.netment.rmi.HelloServiceImpl;

/**
 * Created by jeff on 16/8/2.
 */
public class ServerWithZooKeeperSupport {

    public static void main(String[] args) throws Exception{
        ServiceProvider provider = new ServiceProvider();
        HelloService service = new HelloServiceImpl();
        provider.publish(service , "localhost" , 1099);
    }
}
