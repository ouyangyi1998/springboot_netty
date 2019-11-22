package com.jerry.springboot_netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final ByteBuf HEARTBEAT_SEQUENCE= Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hello Netty", CharsetUtil.UTF_8));
    private int idle_count=1;
    private int count=1;
    private int fcount=1;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接");
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("循环请求的时间："+new Date()+" 次数:"+fcount);
        if(evt instanceof IdleStateEvent)
        {
            IdleStateEvent event=(IdleStateEvent) evt;
            if(IdleState.WRITER_IDLE.equals(event.state()))
            {
                if(idle_count<=5)
                {
                    idle_count++;
                    ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());

                }else
                {
                    System.out.println("不再发送心跳请求");
                }
                fcount++;
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("第"+count+"次"+",客户端接受的消息:"+msg);
        count++;
    }
}
