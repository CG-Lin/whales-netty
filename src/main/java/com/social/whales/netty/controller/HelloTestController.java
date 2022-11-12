package com.social.whales.netty.controller;

import com.social.whales.netty.service.WhalesChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/hello")
public class HelloTestController {
    @Autowired
    WhalesChatGroupService whalesChatGroupService;

    /**
     * 测试Redis是否可用
     * @return
     */
    @GetMapping("/hello/test")
    @ResponseBody
    public void helloTest(){
         whalesChatGroupService.saveWhalesChatGroupOnlineStatus("TwoTestIndex.html",null);
    }
}
