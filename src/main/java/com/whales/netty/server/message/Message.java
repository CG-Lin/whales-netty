package com.whales.netty.server.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qzw
 * @date 2021/9/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /**
     * 消息id
     */
    private String id;

    /**
     * 发送者
     */
    private String send;

    /**
     * 发送者userId
     */
    private String sendUserId;

    /**
     * 接收者
     */
    private String receive;

    /**
     * 接受者userId
     */
    private String receiveUserId;

    /**
     * 文件类型
     * 0 文字 1 图片 2 视频文件 3 一般文件
     */
    private String fileType;

    /**
     * 内容
     */
    private String info;

    /**
     * 消息类型 0 下线 1 上线 2 私人聊天 3 好友申请 4 群上线 5 群聊天
     */
    private int type;

    /**
     * 展示图片标签 只有普通上线与群聊上线才会将头像信息传输
     */
    private String pic;
}
