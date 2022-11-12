package com.social.whales.netty.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *交流群消息内容Vo类
 */
@Data
public class WhalesChatGroupMessageVo {
    /**
     * 交流群账号id
     */
    private String chatGroupId;

    /**
     * 发送该消息的用户id
     */
    private String userId;

    /**
     * 记录发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date messageTime;

    /**
     * 信息标记序列号（群里每有人发一个消息就新增）
     */
    private Long messageSign;

    /**
     * 聊天记录
     */
    private String messageContent;

    /**
     * 聊天记录类型
     */
    private String messageType;
}
