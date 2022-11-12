package com.social.whales.netty.service;

import com.social.whales.netty.vo.WhalesChatGroupMessageVo;

public interface WhalesChatGroupService {

    /**
     * 群成员进群的时候在redis中进行记录
     * @param chatGroupId
     * @param userId
     */
    public void saveWhalesChatGroupOnlineStatus(String chatGroupId,String userId);


    public void saveWhalesChatGroupMessages(WhalesChatGroupMessageVo whalesChatGroupMessageVo);
}
