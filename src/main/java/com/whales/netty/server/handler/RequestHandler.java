package com.whales.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.io.RandomAccessFile;
import java.util.RandomAccess;

public class RequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUri;

    public RequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //equalsIgnoreCase用来比较两个字符串中对应的字符是否相等,会忽略大小写
        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpUtil.is100ContinueExpected(request)) {
                //处理100Continue方式的HTTP
                send100Continue(ctx);
            }
        }
    }

    /*  HTTP/1.1 协议里设计 100 (Continue) HTTP 状态码的的目的是，在客户端发送 Request Message 之前，HTTP/1.1 协议允许客户端先判定服务器是否愿意接受客户端发来的消息主体（基于 Request Headers）。
        即， 客户端 在 Post（较大）数据到服务端之前，允许双方“握手”，如果匹配上了，Client 才开始发送（较大）数据。
        这么做的原因是，如果客户端直接发送请求数据，但是服务器又将该请求拒绝的话，这种行为将带来很大的资源开销。

        协议对 HTTP客户端的要求是：
        如果 client 预期等待“100-continue”的应答，那么它发的请求必须包含一个 " Expect: 100-continue"  的头域！
        如果客户端有POST数据要上传，可以考虑使用100-continue协议。加入头{"Expect": "100-continue"}
        并不是所有的Server都会正确实现100-continue协议，如果Client发送Expect:100-continue消息后，在timeout时间内无响应，Client需要立马上传POST数据。
 */
    private static void send100Continue(ChannelHandlerContext ctx){
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
}
