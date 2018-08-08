package com.philly.asset.controllers;

import com.philly.asset.models.MobileComputer;
import com.philly.asset.models.MobileLog;
import com.philly.asset.models.MobileStatusView;
import com.philly.asset.models.PagerModel;
import com.philly.asset.repositories.MobileComputerRepository;
import com.philly.asset.repositories.MobileLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class MobileStatusController {
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 2;
    private static final int[] PAGE_SIZES = { 10, 20, 50};

    private static final int INITIAL_DAY = 7;
    private static final int[] DAYS = { 1, 7, 14, 30};

    @Autowired
    MobileLogRepository mlRepository;

    @Autowired
    MobileComputerRepository mcRepository;

    //default index method
    @GetMapping("/mobilestatus")
    public ModelAndView index(@RequestParam Optional<Integer> days, @RequestParam("page") Optional<Integer> page, @RequestParam("pageSize") Optional<Integer> pageSize){
        ModelAndView modelAndView = new ModelAndView("mobilestatus/index");
//        ModelAndView m
// odelAndView = new ModelAndView("mobilelog/mobilestatus");

        int evalDays = days.orElse(INITIAL_DAY);
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ?
                INITIAL_PAGE : page.get() - 1;

        LocalDate today = LocalDate.now();
        LocalDate fromDate = today.minusDays(evalDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String start = fromDate.format(formatter);

        String url = "/mobilestatus";

        Iterable<MobileComputer> mobileComputerList =  mcRepository.findAllByOrderByHostNameAsc();
        List<MobileStatusView> mobileStatusViewList = new ArrayList<>();

        for(MobileComputer mc : mobileComputerList){
            List<MobileLog>  mls = mlRepository.findAllByComputerNameAndLogDateGreaterThan(mc.getHostName(),start);
            int distinctDateCount = getDistinctDateCount(mls);
            MobileStatusView msv = new MobileStatusView(mc.getHostName(),mc.getAssetNo(), mc.getModelName(), getProgressBarPercentByUsage(distinctDateCount,evalDays));
            mobileStatusViewList.add(msv);
        }

        Pageable pageable = PageRequest.of(evalPage, evalPageSize);
        Page<MobileStatusView> aa = new PageImpl<>(mobileStatusViewList,pageable, mobileStatusViewList.size());

        PagerModel pager = new PagerModel(aa.getTotalPages(),aa.getNumber(),BUTTONS_TO_SHOW);

        modelAndView.addObject("list",aa);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("days", evalDays);
        modelAndView.addObject("url", url);


        return modelAndView;
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

    public Integer getDistinctDateCount(List<MobileLog> mobileLogList){
        List<String> dates = new ArrayList<>();
        for(MobileLog ml: mobileLogList){
            dates.add(ml.getLogDate());
        }
        List<String> distinctDates = new ArrayList<String>(new HashSet<String>(dates));
        return distinctDates.size();
    }


}
