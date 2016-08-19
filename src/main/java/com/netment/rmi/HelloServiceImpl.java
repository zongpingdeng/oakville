package com.netment.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by jeff on 16/8/1.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {


    public HelloServiceImpl() throws RemoteException{};

    @Override
    public String sayHello(String hello) throws RemoteException {
        System.out.println("Handling remote method call...");
        return String.format("Hello %s" , hello);
    }

}
