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

public class ServerHandleInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("responseEncode",new HttpResponseEncoder());
        channelPipeline.addLast("requestDecode",new HttpRequestDecoder());

        channelPipeline.addLast("objectAggregator",new HttpObjectAggregator(1024));
        channelPipeline.addLast("contentCompressor",new HttpContentCompressor());

        channelPipeline.addLast("httpServerHandler", new HttpServerHandler());

    }
}
