package com.philly.asset.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //for testing
    @RequestMapping("/hello")
    public String hello(){
        return "Hello, world";
    }



}