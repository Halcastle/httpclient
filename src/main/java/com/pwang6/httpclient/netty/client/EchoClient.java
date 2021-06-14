package com.pwang6.httpclient.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.log4j.Log4j2;

import java.net.InetSocketAddress;

/**
  *@ClassName EchoClient
  *@Description TODO
  *@author Dream
  *@Date 2021-06-13 17:07
  *@Version 1.0
  **/
@Log4j2
public class EchoClient {
    private final String host;
    private final  int port;

    public EchoClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 9001;
        new EchoClient(host,port).start();
    }
}
