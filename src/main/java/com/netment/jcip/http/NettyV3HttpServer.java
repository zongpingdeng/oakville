package com.netment.jcip.http;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by jeff on 16/5/25.
 */
public class NettyV3HttpServer {

    private static final Logger logger = Logger.getLogger(NettyV3HttpServer.class);

    public static void main(String[] args) {

        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new HttpServerPipelineFactory());

        bootstrap.bind(new InetSocketAddress(8080));

        logger.info("服务器已经启动，请访问http://127.0.0.1:8080/index.html进行测试！");
    }
}
