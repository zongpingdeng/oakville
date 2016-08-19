package com.netment.rmi;

import java.rmi.Naming;

/**
 * Created by jeff on 16/8/1.
 */
public class HelloRmiClient {

    public static void main(String[] args) throws Exception{
        String url = "rmi://localhost:1099/demo.zookeeper.remoting.server.HelloServiceImpl";
        HelloService helloService = (HelloService) Naming.lookup(url);
        String result = helloService.sayHello("deng");
        System.out.println(result);
    }
}
