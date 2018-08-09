package com.philly.asset.controllers;

import com.philly.asset.models.MobileLog;
import com.philly.asset.models.PagerModel;
import com.philly.asset.repositories.MobileComputerRepository;
import com.philly.asset.repositories.MobileLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

@Controller
public class MobileLogController {

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 20;
    private static final int[] PAGE_SIZES = { 10, 20, 50};

    @Autowired
    MobileLogRepository mlRepository;

    @Autowired
    MobileComputerRepository mcRepository;

    //default index method
    @GetMapping("/mobilelog")
    public ModelAndView index(@RequestParam("page") Optional<Integer> page, @RequestParam("pageSize") Optional<Integer> pageSize){
        ModelAndView modelAndView = new ModelAndView("mobilelog/index");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        String url = "/mobilelog";

        Page<MobileLog> mobileLogList = mlRepository.findAllByOrderByDateAndtimeDesc(new PageRequest(evalPage, evalPageSize));
        PagerModel pager = new PagerModel(mobileLogList.getTotalPages(),mobileLogList.getNumber(),BUTTONS_TO_SHOW);

        modelAndView.addObject("mobileLogList",mobileLogList);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("url", url);
        return modelAndView;
    }

    @GetMapping("/mobilelog/{hostName}")
    public ModelAndView indexByHostName(@RequestParam("page") Optional<Integer> page, @RequestParam("pageSize") Optional<Integer> pageSize, @PathVariable Optional<String> hostName){
        ModelAndView modelAndView = new ModelAndView("mobilelog/index");
        String evalHostName ;
        Page<MobileLog> mobileLogList;
        PagerModel pager;

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        String url ;

        if(!hostName.isPresent()){
            //do something.
        } else{
            evalHostName = hostName.get();
            url = "/mobilelog/"+ evalHostName;

            mobileLogList = mlRepository.findAllByComputerNameOrderByDateAndtimeDesc(evalHostName, new PageRequest(evalPage, evalPageSize));
            pager = new PagerModel(mobileLogList.getTotalPages(),mobileLogList.getNumber(),BUTTONS_TO_SHOW);
            modelAndView.addObject("hostName",evalHostName);
            modelAndView.addObject("mobileLogList",mobileLogList);
            modelAndView.addObject("selectedPageSize", evalPageSize);
            modelAndView.addObject("pageSizes", PAGE_SIZES);
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("url", url);
        }
        return modelAndView;
    }

    //needs  to fix
    @GetMapping("/mobilelog/search")
    public String searchMobileLogs(@RequestParam Optional<String> aname, Model model){
        if(!aname.isPresent()){
            return "redirect:/mobilelog";
        } else {
            List<MobileLog> mls = mlRepository.findAllByComputerNameOrderByDateAndtimeDesc(aname.get().toUpperCase());
            model.addAttribute("mobilelogs", mls);
            return "mobilelog";
        }
    }
}
