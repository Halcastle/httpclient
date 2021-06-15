package com.pwang6.httpclient.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

public interface IHttpResponseFilter {
    void filter(FullHttpResponse fullHttpResponse, ChannelHandlerContext ctx);
}
