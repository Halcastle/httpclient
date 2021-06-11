package com.pwang6.httpclient.netty.inbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        String rtnMsg = "";
        //获得uri
        //获得数据
        //获得请求方式
        String uri = httpRequest.uri();
        String data = httpRequest.content().toString(CharsetUtil.UTF_8);
        HttpMethod method = httpRequest.method();

        //判断请求链接是否合法
        if(!"/http/get".equals(uri)){
            rtnMsg = "请求路径：【"+uri+"】不合法！receve";
            resopnseHandle(rtnMsg,ctx,HttpResponseStatus.BAD_REQUEST);
            return;
        }
        if(HttpMethod.GET.equals(method)){
            log.info("接收到【{}】的GET请求",uri);
            rtnMsg = "服务端接收到数据，数据内容："+data;
            resopnseHandle(rtnMsg,ctx,HttpResponseStatus.OK);
        }
        if(HttpMethod.POST.equals(method)){
            log.info("接收到【{}】的POST请求",uri);
            rtnMsg = "服务端接收到数据，数据内容："+data;
            resopnseHandle(rtnMsg,ctx,HttpResponseStatus.OK);
        }

    }

    //处理请求数据，并将数据向下传输
    private void resopnseHandle(String rtnMsg, ChannelHandlerContext cx, HttpResponseStatus httpResponseStatus){
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,httpResponseStatus, Unpooled.copiedBuffer(rtnMsg,CharsetUtil.UTF_8));
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;chartset=UTF-8");
        cx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}