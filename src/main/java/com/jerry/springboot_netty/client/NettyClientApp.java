package com.jerry.springboot_netty.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NettyClientApp {
    public static void main(String[] args) throws Exception{
        ApplicationContext context= SpringApplication.run(NettyClientApp.class,args);
        NettyClient nettyClient=context.getBean(NettyClient.class);
        nettyClient.run();
    }
}
