package com.pwang6.httpclient.netty.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @author Dream
 */
@Log4j2
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        log.info("Server recevied:{}",in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端有连接连入，开始写数据：{}","我是服务端的消息，serverMSG！");
        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端的消息，serverMSG！", CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
