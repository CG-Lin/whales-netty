package com.social.whales.netty.vo;

import lombok.Data;

/**
 * 交流群成员在线记录Vo类
 */
@Data
public class WhalesChatGroupOnlineVo {
    /**
     * 交流群账号id
     */
    private String chatGroupId;

    /**
     * 发送该消息的用户id
     */
    private String userId;
}
