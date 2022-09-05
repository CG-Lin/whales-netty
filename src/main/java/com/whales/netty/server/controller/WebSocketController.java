package com.whales.netty.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebSocketController {

    @GetMapping("/community")
    public String webSocketHtml(){
        return "helloTestIndex.html";
    }

    @GetMapping("/indexWebSocket")
    public String indexHtml(){
        return "indexWebSocket.html";
    }
}
