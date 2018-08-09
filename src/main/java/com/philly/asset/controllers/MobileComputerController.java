package com.philly.asset.controllers;

import com.philly.asset.models.MobileComputer;
import com.philly.asset.models.PagerModel;
import com.philly.asset.repositories.MobileComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class MobileComputerController {
    @Autowired
    MobileComputerRepository mcRepository;

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;
    private static final int[] PAGE_SIZES = { 5, 10, 20};

    @RequestMapping("/mc")
    public ModelAndView product(@RequestParam("aname") Optional<String> aname, @RequestParam("page") Optional<Integer> page, @RequestParam("pageSize") Optional<Integer> pageSize) {
        ModelAndView modelAndView = new ModelAndView("mc/index");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        String url = "/mc";

        Page<MobileComputer> mobileComputerList;
        PagerModel pager;

        if(aname.isPresent()){
            mobileComputerList = mcRepository.findAllByHostNameLikeOrderByHostNameAsc(aname.get(), new PageRequest(evalPage, evalPageSize));
        }else {

            mobileComputerList = mcRepository.findAllByOrderByHostNameAsc(new PageRequest(evalPage, evalPageSize));
        }
        pager = new PagerModel(mobileComputerList.getTotalPages(),mobileComputerList.getNumber(),BUTTONS_TO_SHOW);

        modelAndView.addObject("list",mobileComputerList);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("url", url);
        return modelAndView;
    }

    @RequestMapping("/mc/create")
    public String create(Model model) {
        return "mc/create";
    }

    @RequestMapping("/mc/save")
    public String save(@RequestParam String hostName, @RequestParam String assetNo, @RequestParam String modelName) {
        MobileComputer mc = new MobileComputer(hostName,assetNo,modelName);
        mcRepository.save(mc);

        return "redirect:/mc/" + mc.getId();
    }

    @RequestMapping("/mc/{id}")
    public String show(@PathVariable String id, Model model) {

        model.addAttribute("mc", mcRepository.findById(id).get());
        return "mc/show";
    }

    @RequestMapping("/mc/delete")
    public String delete(@RequestParam String id) {
        MobileComputer mc = mcRepository.findById(id).get();
        mcRepository.delete(mc);

        return "redirect:/mc";
    }

    @RequestMapping("/mc/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("mc", mcRepository.findById(id).get());
        return "mc/edit";
    }

    @RequestMapping("/mc/update")
    public String update(@RequestParam String id, @RequestParam String hostName, @RequestParam String assetNo, @RequestParam String modelName) {
        MobileComputer mc = mcRepository.findById(id).get();
        mc.setAssetNo(assetNo);
        mc.setHostName(hostName);
        mc.setModelName(modelName);
        mcRepository.save(mc);

        return "redirect:/mc/" + mc.getId();
    }
}