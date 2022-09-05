package com.whales.netty.server.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.whales.netty.server.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channelGroup;

    /**
     * 存储用户对应的通道
     * ChannelHandlerContext 代表了 ChannelHandler 和 ChannelPipeline 之间的关联，ChannelHandlerContext 的主要功能是管理它所关联的 ChannelHandler 和在
     * 同一个 ChannelPipeline 中的其他 ChannelHandler 之间的交互
     */
    private final Map<String, ChannelHandlerContext> channelHandlerContextMap = new ConcurrentHashMap<>();

    private final List<ChannelHandlerContext> channelHandleContextList = new CopyOnWriteArrayList<>();


    public TextWebSocketFrameHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandleContextList.add(ctx);
        //TODO 测试群聊的
        System.out.println("新的链接进入：" + ctx.channel().remoteAddress() + ",连接总数量");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("textWebSocketFrame go in,Then message is :"+textWebSocketFrame.text());
        //前端发来信息，转为message
        Message message = JSONObject.parseObject(textWebSocketFrame.text(), Message.class);
        //处理客户端（发送者）的聊天信息-单对单
        //接收人id
        String receiveUserId = message.getReceiveUserId();
        //接收者
        String receive = message.getReceive();
        //发送者
        String send = message.getSend();
        //发送者id
        String sendUserId = message.getSendUserId();
        //从缓存中去查出的用户通道
        //TODO 引入redis，将用户ID和用户连接
         ChannelHandlerContext channelHandlerContexts = channelHandlerContextMap.get(receiveUserId);
        if (channelHandlerContexts != null) {
            //TODO 头像也要存在redis好拿取
            Message messageSend = new Message(UUID.randomUUID().toString(), send, sendUserId, receive, receiveUserId, message.getInfo(), message.getFileType(), message.getType(), null);
            channelHandlerContexts.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageSend)));
        }
/*        channelHandlerContexts.writeAndFlush(messageSend);
        //增加引用计数，避免被释放，并且输出
        channelGroup.writeAndFlush(textWebSocketFrame.retain());*/
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
