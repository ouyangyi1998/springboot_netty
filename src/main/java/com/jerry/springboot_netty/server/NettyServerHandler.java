package com.jerry.springboot_netty.server;
import java.net.InetAddress;
import java.util.Date;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private int idle_count=1;
    private int count=1;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent)
        {
            IdleStateEvent event=(IdleStateEvent)evt;
            if(IdleState.READER_IDLE.equals(event.state()))
            {
                System.out.println("已经五秒没有收到信号了");
                if(idle_count>2)
                {
                    System.out.println("关闭channel");
                    ctx.channel().close();
                }
                idle_count++;
            }else
            {
                super.userEventTriggered(ctx,evt);
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("第"+count+"次，服务器接收到的消息为"+msg);
        String message=(String)msg;
        if("Hello Netty".equals(message))
        {
            System.out.println("服务器收到心跳消息");
            ctx.flush();
        }
        count++;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
