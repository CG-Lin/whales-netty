package com.whales.netty.server.service;

import com.whales.netty.server.handler.StompWebSocketFrameHandler;
import com.whales.netty.server.handler.TextWebSocketFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.stomp.StompSubframeDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {

    private final ChannelGroup channelGroup;

    public ChatServer(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    public void severSide() {
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        try{
            bootstrap.group(boss,work).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {

                    //将字节解码为HttpRequest、HttpContent和LastHttpContent
                    socketChannel.pipeline().addLast(new HttpServerCodec());
                    //写入一个文件的内容
                    socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                    //将一个HttpMessage和跟随它的多个HttpContent聚合为单个FullHttpRequest或则FullHttpResponse。安装完后ChannelPipeline中下一个ChannelHandler将只会收到完整的Http请求或相应
                    socketChannel.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
                    //按照WebSocket规范要求。处理websocket升级握手
                    socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));

                    socketChannel.pipeline().addLast(new TextWebSocketFrameHandler(channelGroup));
                    //对Stomp帧数进行节码
                    socketChannel.pipeline().addLast(new StompSubframeDecoder());

                    socketChannel.pipeline().addLast(new StompWebSocketFrameHandler());

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
