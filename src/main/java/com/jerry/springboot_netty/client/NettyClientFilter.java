package com.jerry.springboot_netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {
    @Autowired
    private NettyClientHandler nettyClientHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline ph=socketChannel.pipeline();

        ph.addLast(new IdleStateHandler(0,4,0,TimeUnit.SECONDS));
        ph.addLast("decoder",new StringDecoder());
        ph.addLast("encode",new StringEncoder());
        ph.addLast("handler",nettyClientHandler);
    }
}
