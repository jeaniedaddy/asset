package com.philly.asset.controllers;

import com.philly.asset.models.MobileLog;
import com.philly.asset.repositories.MobileLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MobileLogController {
    @Autowired
    MobileLogRepository mlRepository;

    @GetMapping("/mobilelog/mobilelogs")
    public String mobileLogs(Model model)
    {
        model.addAttribute("mobilelogs", mlRepository.findAll());
        return "mobilelog/mobilelogs";
    }

}
