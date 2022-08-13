package com.whales.netty.server.session.impl;

import com.whales.netty.server.session.RecordSession;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//暂时设定存放在内存中
@Service
public class RecordSessionMemoryImpl implements RecordSession {

    //username和channel关联
    private final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
    //channel和username关联
    private final Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUsernameMap.put(channel, username);
    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        usernameChannelMap.remove(username);
    }

    @Override
    public Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }
}
