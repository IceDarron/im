package com.icedarron.im.webapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello,this is a springboot demo...";
    }

    public void main(String[] args){
        System.out.print("/");
    }
}



