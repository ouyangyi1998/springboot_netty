package com.jerry.springboot_netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NettyServer {
    private static final int port=9876;
    private static EventLoopGroup group=new NioEventLoopGroup();
    private static ServerBootstrap b=new ServerBootstrap();

    @Autowired
    private NettyServerFilter nettyServerFilter;

    public void run()
    {
        try {
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(nettyServerFilter);
            ChannelFuture f=b.bind(port).sync();
            System.out.println("服务器端成功启动，端口为："+port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
