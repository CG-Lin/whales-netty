package com.whales.netty.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RequestMapping("/hello")
public class HelloTestController {
    @GetMapping("/hello/test")
    public String helloTest(){
        return "TwoTestIndex.html";
    }
}
