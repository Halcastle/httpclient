package com.pwang6.httpclient.netty.init;

import com.pwang6.httpclient.netty.inbound.HttpServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
  *@ClassName ServerHandleInit
  *@Description TODO
  *@Author Dream
  *@Date 2021-06-13 17:10
  *@Version 1.0
  **/
public class ServerHandleInit extends ChannelInitializer<SocketChannel> {
    private final List<String> proxyServer;

    public ServerHandleInit(List<String> proxyServer){
        this.proxyServer = proxyServer.stream().map(this::formatUrl).collect(Collectors.toList());
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("responseEncode",new HttpResponseEncoder());
        channelPipeline.addLast("requestDecode",new HttpRequestDecoder());

        channelPipeline.addLast("objectAggregator",new HttpObjectAggregator(1024));
        channelPipeline.addLast("contentCompressor",new HttpContentCompressor());

        channelPipeline.addLast("httpServerHandler", new HttpServerHandler(proxyServer));

    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }
}
