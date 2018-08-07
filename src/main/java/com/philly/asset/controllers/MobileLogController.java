package com.philly.asset.controllers;

import com.philly.asset.models.MobileComputer;
import com.philly.asset.models.MobileLog;
import com.philly.asset.models.MobileStatusView;
import com.philly.asset.repositories.MobileComputerRepository;
import com.philly.asset.repositories.MobileLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MobileLogController {
    private static int currentPage = 1;
    private static int pageSize = 5;

    @Autowired
    MobileLogRepository mlRepository;

    @Autowired
    MobileComputerRepository mcRepository;

    @GetMapping("/mobilelog/index/{hostName}")
    public String pageTest(@PathVariable Optional<String> hostName,Model model , @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<MobileLog> mobileLogPage ;
        if(hostName.isPresent()){
            mobileLogPage = mlRepository.findAllByComputerNameOrderByDateAndtimeDesc(hostName.get(), PageRequest.of(currentPage -1, pageSize));
        } else {
            mobileLogPage = mlRepository.findAllByComputerNameOrderByDateAndtimeDesc( "", PageRequest.of(currentPage -1, pageSize));
        }

        model.addAttribute("mobileLogPage", mobileLogPage);
        int totalPages = mobileLogPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers); 

        }

        return "mobilelog/mobilelogs02";
    }

    @GetMapping({"/mobilelog/mobilelogs/{hostName}", "/mobilelog/mobilelogs"})
    public String mobileLogs(@PathVariable Optional<String> hostName, Model model)

    {
        List<MobileLog> mls ;
        if(hostName.isPresent()){
//            findAllByComputerNameOrderByDateAndtimeDesc
            mls = mlRepository.findAllByComputerNameOrderByDateAndtimeDesc(hostName.get());
            mls = mlRepository.findAllByComputerNameOrderByDateAndtimeDesc(hostName.get());
//            mls = mlRepository.findAllByComputerNameOrderByIdDateAndtimeDesc(hostName.get());
            //findAllByComputerNameContainsOrderByIdIdDesc
        } else {
            mls = mlRepository.findAll();
        }

        model.addAttribute("mobilelogs", mls);
        return "mobilelog/mobilelogs";
    }

    @GetMapping("/mobilelog/search")
    public String searchMobileLogs(@RequestParam Optional<String> aname, Model model){
        if(!aname.isPresent()){
            return "redirect:/mobilelog/mobilelogs";
        } else {
            List<MobileLog> mls = mlRepository.findAllByComputerNameOrderByDateAndtimeDesc(aname.get().toUpperCase());
//            List<MobileLog> mls = mlRepository.findAllByComputerNameContains(aname.get().toUpperCase());
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

        Sort sort = new Sort(Sort.Direction.ASC, "hostName");

        List<MobileComputer> mcs = mcRepository.findAll(sort);
//        List<MobileComputer> mcs = mcRepository.findAllOrderByHostName();
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
