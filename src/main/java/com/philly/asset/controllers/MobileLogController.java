package com.philly.asset.controllers;

import com.mongodb.WriteResult;
import com.philly.asset.models.MobileComputer;
import com.philly.asset.models.MobileLog;
import com.philly.asset.models.MobileStatusView;
import com.philly.asset.repositories.MobileComputerRepository;
import com.philly.asset.repositories.MobileLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.DateUtils;

import javax.swing.text.html.Option;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class MobileLogController {
    @Autowired
    MobileLogRepository mlRepository;

    @Autowired
    MobileComputerRepository mcRepository;

    @RequestMapping({"/mobilelog/mobilelogs/{hostName}", "/mobilelog/mobilelogs"})
    public String mobileLogs(@PathVariable Optional<String> hostName, Model model)
    {
        List<MobileLog> mls = new ArrayList<>();
        if(hostName.isPresent()){
            mls = mlRepository.findAllByComputerName(hostName.get());
        } else {
            mls = mlRepository.findAll();
        }

        model.addAttribute("mobilelogs", mls);
        return "mobilelog/mobilelogs";
    }

    @RequestMapping({"/mobilelog/search/{name}", "/mobilelog/search/"})
    public String searchMobileLogs(@PathVariable Optional<String> name, Model model){
        if(!name.isPresent()){
            return "redirect:/mobilelog/mobilelogs";
        } else {
            List<MobileLog> mls = mlRepository.findAllByComputerNameContains(name.get());
            model.addAttribute("mobilelogs", mls);
            return "mobilelog/mobilelogs";
        }
    }


    @GetMapping({"/mobilelog/mobilestatus/{days}", "/mobilelog/mobilestatus"})
    public String mobileLogs4(@PathVariable Optional<Integer> days, Model model)
    {
        Integer newDays = 7;
        if(days.isPresent()){
            newDays = days.get();
        }

        LocalDate today = LocalDate.now();
        LocalDate fromDate = today.minusDays(newDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String start = fromDate.format(formatter);

        List<MobileComputer> mcs = mcRepository.findAll();
        //List<MobileComputer> mcs = mcRepository.findAllOrderByHostName();
        List<MobileStatusView> msvs = new ArrayList<>();

        for(MobileComputer mc : mcs){
            List<MobileLog>  mls = mlRepository.findAllByComputerNameAndLogDateGreaterThan(mc.getHostName(),start);
            MobileStatusView msv = new MobileStatusView(mc.getHostName(),mc.getAssetNo(), mc.getModelName(), getProgressBarPercentByUsage(mls.size(),newDays));
            msvs.add(msv);

        }

        model.addAttribute("mobilestatus", msvs);
        return "mobilelog/mobilestatus";
    }

    // small utilities
    public Integer getProgressBarPercentByUsage(Integer usage, Integer days){
        int percent = (usage * 100) / days;
        if(percent > 100 ){
            return 100;
        } else {
            return percent;
        }
    }

}
