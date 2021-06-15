package com.pwang6.httpclient.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.log4j.Log4j2;

/**
 * @ClassName HeaderHttpRequestFilter
 * @Description TODO
 * @Author pwang6
 * @Date 2021/6/15 9:11
 * @Version 1.0
 **/
@Log4j2
public class HeaderHttpRequestFilter extends ChannelInboundHandlerAdapter implements IHttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        String acceptHeader = fullHttpRequest.headers().get("Accept");
        HttpHeaders httpheaders = fullHttpRequest.headers();
        log.info("AcceptHeader的值为：{}",acceptHeader);
        log.info("httpheaders的值为：{}",httpheaders);
        fullHttpRequest.headers().add("wpadd1","pwang6");
        fullHttpRequest.headers().set("wpadd2","pwang6");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        filter(fullHttpRequest,ctx);
        String wpadd1 = fullHttpRequest.headers().get("wpadd1");
        String wpadd2 = fullHttpRequest.headers().get("wpadd2");
        log.info("wpadd1的值为：{}",wpadd1);
        log.info("wpadd2的值为：{}",wpadd2);
        ctx.writeAndFlush(fullHttpRequest);
    }
}
