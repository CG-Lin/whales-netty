package com.social.whales.netty.service.impl;

import com.social.whales.netty.service.WhalesChatGroupOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WhalesChatGroupOnlineServiceImpl implements WhalesChatGroupOnlineService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean saveWhalesChatGroupOnlineStatus(String chatGroupId, String userId) {
        Boolean aBoolean = redisTemplate.hasKey(chatGroupId);
        System.out.println(aBoolean);
        return aBoolean;
    }
}
