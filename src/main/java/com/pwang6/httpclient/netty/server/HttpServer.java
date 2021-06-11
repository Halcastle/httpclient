package com.pwang6.httpclient.netty.server;

import com.pwang6.httpclient.netty.init.ServerHandleInit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HttpServer {

    public static final int port = 8888;
    public static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    public static EventLoopGroup workGroup = new NioEventLoopGroup(16);
    public static ServerBootstrap serverBootstrap = new ServerBootstrap();
    public static void main(String[] args) {
        try {
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerHandleInit());
            ChannelFuture future = serverBootstrap.bind(port).sync();
            log.info("启动端口为：{}的HTTP服务器",port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
