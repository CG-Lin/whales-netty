package com.whales.netty.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    @GetMapping("/websocket")
    public String webSocketHtml(){
        return "helloTestIndex.html";
    }
}
