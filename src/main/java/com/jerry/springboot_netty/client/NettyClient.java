package com.jerry.springboot_netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("nettyClient")
public class NettyClient {
    private String host="127.0.0.1";
    private int port=9876;
    private EventLoopGroup group=new NioEventLoopGroup();
    private static Channel ch;
    private static Bootstrap b=new Bootstrap();
    @Autowired
    private NettyClientFilter nettyClientFilter;

    public void run() throws Exception
    {
        System.out.println("客户端成功启动");
        b.group(group);
        b.channel(NioSocketChannel.class);
        b.handler(nettyClientFilter);
        ch=b.connect(host,port).sync().channel();
    }

}
