package com.pwang6.httpclient.netty.server;

import com.pwang6.httpclient.netty.init.ServerHandleInit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

/**
 * @author Dream
 */
@Log4j2
public class HttpServer {

    public static final int PORT = 8888;
    public static final String PROXY_SERVER = "http://localhost:8080";

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(16);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerHandleInit(Arrays.asList(PROXY_SERVER.split(","))));
            ChannelFuture future = serverBootstrap.bind(PORT).sync();
            log.info("启动端口为：{}的HTTP服务器", PORT);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
