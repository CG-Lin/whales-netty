package com.whales.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channelGroup;

    /**
     * 存储用户对应的通道
     */
    private final Map<String, List<ChannelHandlerContext>> channelHandlerContextMap = new ConcurrentHashMap<>();



    public TextWebSocketFrameHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //TODO 测试群聊的
        System.out.println("新的链接进入：" + ctx.channel().remoteAddress() + ",连接总数量" + ch);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //增加引用计数，避免被释放，并且输出
        channelGroup.writeAndFlush(textWebSocketFrame.retain());
    }

    //自定义事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断是否握手成功，通道升级到websockets
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            //如果握手成功说明升级成了websocket协议
            channelGroup.writeAndFlush(new TextWebSocketFrame("Client:" + ctx.channel() + "Joined"));
            //将新的websocket channel加入到channel group 以便后面它接收到所有消息
            channelGroup.add(ctx.channel());
        } else {
            //转发到ChannelPipeline中的下一个channelBoundHandler;
            super.userEventTriggered(ctx, evt);
        }

    }

}
