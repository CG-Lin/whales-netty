package com.whales.netty.server.handler;

import com.alibaba.fastjson2.JSONObject;
import com.whales.netty.server.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.stomp.DefaultStompFrame;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeaders;

import java.nio.charset.StandardCharsets;

public class StompWebSocketFrameHandler extends SimpleChannelInboundHandler<StompFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StompFrame stompFrame) throws Exception {
        switch (stompFrame.command()) {
            case RECEIPT:
                StompFrame frame = new DefaultStompFrame(StompCommand.SEND);
                //frame.headers().set(StompHeaders.DESTINATION,StompClient.TOPIC);
                frame.content().writeBytes("some payload".getBytes());
                ctx.writeAndFlush(frame);
                break;
        }
        String messageContent = stompFrame.content().toString(StandardCharsets.UTF_8);
        //前端发来信息，转为message
        Message message = JSONObject.parseObject(messageContent, Message.class);
    }
}
