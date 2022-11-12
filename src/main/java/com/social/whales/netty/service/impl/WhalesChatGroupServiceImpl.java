package com.social.whales.netty.service.impl;

import com.alibaba.fastjson.JSON;
import com.social.whales.netty.service.WhalesChatGroupService;
import com.social.whales.netty.vo.WhalesChatGroupMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhalesChatGroupServiceImpl implements WhalesChatGroupService {

    private static final String GROUP_CHAT_ONLINE_LOG = "group_chat_online_log";
    private static final String GROUP_CHAT_MESSAGE = "group_chat_message";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 群成员进群的时候在redis中进行记录
     *
     * @param chatGroupId
     * @param userId
     */
    @Override
    public void saveWhalesChatGroupOnlineStatus(String chatGroupId, String userId) {
        String redisKey = GROUP_CHAT_ONLINE_LOG + ":" + chatGroupId;
        List<String> userIdList = redisTemplate.opsForList().range(redisKey, 0, -1);
        if (userIdList != null) {
            long count = userIdList.stream().filter(id -> id.equals(userId)).count();
            if (count <= 0) {
                redisTemplate.opsForList().leftPush(redisKey, userId);
            }
        } else {
            redisTemplate.opsForList().leftPush(redisKey, userId);
        }
    }

    @Override
    public void saveWhalesChatGroupMessages(WhalesChatGroupMessageVo whalesChatGroupMessageVo) {
        String chatGroupId = whalesChatGroupMessageVo.getChatGroupId();
        String redisKey = GROUP_CHAT_MESSAGE + ":" + chatGroupId;
        //先查询当前群共有几条消息
        Long size = redisTemplate.opsForList().size(redisKey);
        //将消息置入标记中
        whalesChatGroupMessageVo.setMessageSign(size);
        //存入redis中
        redisTemplate.opsForList().leftPush(redisKey, JSON.toJSONString(whalesChatGroupMessageVo));
    }

}
