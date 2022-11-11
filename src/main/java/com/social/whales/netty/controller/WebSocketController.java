package com.social.whales.netty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    /**
     * 测试打开WebSocket首页
     * @return
     */
    @GetMapping("/community")
    public String webSocketHtml(){
        return "helloTestIndex.html";
    }

    @GetMapping("/indexWebSocket")
    public String indexHtml(){
        return "indexWebSocket.html";
    }
}
