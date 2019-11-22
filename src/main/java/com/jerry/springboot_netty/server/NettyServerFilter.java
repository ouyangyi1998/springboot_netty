package com.jerry.springboot_netty.server;

import com.jerry.springboot_netty.client.NettyClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline ph=socketChannel.pipeline();
        ph.addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
        ph.addLast("decoder",new StringDecoder());
        ph.addLast("encoder",new StringEncoder());
        ph.addLast("handler",new NettyServerHandler());
    }
}
