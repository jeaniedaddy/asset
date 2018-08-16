package com.philly.asset.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(){
        return "redirect:/mobilestatus";
    }

    @RequestMapping("/about")
    public String about(){
        return "home/about";
    }

    @GetMapping("/login")
    public String login(){
        return "home/login";
    }

    @PostMapping("/login")
    public String loggnedIn(){
        return "redirect:/mobilestatus";
    }
}
