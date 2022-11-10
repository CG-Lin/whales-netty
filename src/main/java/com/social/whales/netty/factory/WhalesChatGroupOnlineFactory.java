package com.social.whales.netty.factory;

import com.social.whales.netty.service.WhalesChatGroupOnlineService;
import com.social.whales.netty.service.impl.WhalesChatGroupOnlineServiceImpl;

public abstract class WhalesChatGroupOnlineFactory {

    private static WhalesChatGroupOnlineService  whalesChatGroupOnlineService = new WhalesChatGroupOnlineServiceImpl();

    public static WhalesChatGroupOnlineService getWhalesChatGroupOnlineService(){
        return whalesChatGroupOnlineService;
    }
}
