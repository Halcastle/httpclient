package com.pwang6.httpclient.netty.inbound;

import com.pwang6.httpclient.util.OkHttpUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * @author Dream
 */
@Log4j2
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private final List<String> proxyServer;

    public HttpServerHandler(List<String> proxyServer){
        this.proxyServer = proxyServer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        String rtnMsg = "";
        //获得uri
        //获得数据
        //获得请求方式
        String uri = httpRequest.uri();
        String getString = "/http/demo/get";
        String data = httpRequest.content().toString(CharsetUtil.UTF_8);
        HttpMethod method = httpRequest.method();

        //判断请求链接是否合法
        if(!getString.equals(uri)){
            rtnMsg = "请求路径：【"+uri+"】不合法！receve";
            resopnseHandle(rtnMsg,ctx,HttpResponseStatus.BAD_REQUEST);
            return;
        }
        if(HttpMethod.GET.equals(method)){
            log.info("接收到【{}】的GET请求，将转发到【{}】",uri,proxyServer.get(0)+uri);
            Response response = OkHttpUtil.getDemo(proxyServer.get(0)+uri);
//            rtnMsg = response.body().string();
//            resopnseHandle(rtnMsg,ctx,HttpResponseStatus.OK);
            resopnseHandle1(response,ctx);
        }
        if(HttpMethod.POST.equals(method)){
            log.info("接收到【{}】的POST请求",uri);
            rtnMsg = "服务端接收到数据，数据内容："+data;
            resopnseHandle(rtnMsg,ctx,HttpResponseStatus.OK);
        }

    }

    /**
     * 处理请求数据，并将数据向下传输
     * @param rtnMsg
     * @param cx
     * @param httpResponseStatus
     */
    private void resopnseHandle(String rtnMsg, ChannelHandlerContext cx, HttpResponseStatus httpResponseStatus){
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,httpResponseStatus, Unpooled.copiedBuffer(rtnMsg,CharsetUtil.UTF_8));
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;chartset=UTF-8");
        cx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }
    private void resopnseHandle1(Response response, ChannelHandlerContext cx) throws IOException {
//        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,httpResponseStatus, Unpooled.copiedBuffer(rtnMsg,CharsetUtil.UTF_8));
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.valueOf(response.protocol().toString()),HttpResponseStatus.valueOf(response.code()), Unpooled.copiedBuffer(response.body().string(),CharsetUtil.UTF_8));
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;chartset=UTF-8");
        cx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
