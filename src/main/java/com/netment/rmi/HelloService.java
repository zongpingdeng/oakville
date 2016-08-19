package com.netment.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jeff on 16/8/1.
 */
public interface HelloService extends Remote {

    String sayHello(String hello) throws RemoteException;

}
