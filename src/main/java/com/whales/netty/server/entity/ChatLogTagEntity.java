package com.whales.netty.server.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ChatLogTagEntity {
    /**
     * 交流群账号id
     */
    private String communicationGroupId;

    /**
     * 发送该消息的用户id
     */
    private String userId;

    /**
     * 记录发送时间
     */
    private Date userInformationTime;

    /**
     * 信息标记序列号（群里每有人发一个消息就）
     */
    private String informationSign;

    /**
     * 聊天记录
     */
    private String userInformation;

    /**
     * 聊天记录类型
     */
    private String informationType;
}