package com.netment.zookeeper;

import com.netment.rmi.HelloService;

/**
 * Created by jeff on 16/8/2.
 */
public class ClientWithZooKeeperSupport {

    public static void main(String[] args) throws Exception{
        ServiceConsumer consumer = new ServiceConsumer();
        HelloService service = consumer.lookup();
        service.sayHello("Mr Deng");
    }
}
